package visitors;

import expressions.*;
import program.Context;


/*
 * Visitor to calculate the total distance traveled by the turtle. Not all
 * nodes result in the turtle moving so some nodes have blank methods.
 */
public class DistanceCalculationVisitor implements IVisitor {

    private double distance;
    private Context context;


    public DistanceCalculationVisitor(Context context) {
        this.context = context;
    }


    public double getDistance() {
        return distance;
    }


    @Override
    public void visitAssignment(AssignmentExpression assignmentExpression) {
        // We need to update the context, in case the variable was reassigned
        assignmentExpression.interpret(context);
    }

    /*
     * We'll only reach here from MoveExpression so we can safely add the
     * value of the constant as distance traveled.
     */
    @Override
    public void visitConstant(ConstantExpression constantExpression) {
        distance += constantExpression.interpret(context);
    }

    /*
     * We'll only reach here from MoveExpression so we can safely add the
     * value of the variable as distance traveled.
     */
    @Override
    public void visitVariable(VariableExpression variableExpression) {
        distance += variableExpression.interpret(context);
    }

    /*
     * Since the turtle will move, we'll visit the value of the movement node
     * and add the distance traveled in that node.
     */
    @Override
    public void visitMove(MoveExpression moveExpression) {
        moveExpression.getValueExpression().accept(this);
    }

    @Override
    public void visitTurn(TurnExpression turnExpression) {
        // do nothing.
    }

    /*
     * There are multiple nodes in the repeat expression so we visit all of
     * them.
     */
    @Override
    public void visitRepeat(RepeatExpression repeatExpression) {
        ITerminalExpression numberOfRepetitionsExpression =
                repeatExpression.getNumberOfRepetitionsExpression();
        double numberOfRepetitions =
                numberOfRepetitionsExpression.interpret(context);

        for(int i = 0; i < numberOfRepetitions; i++) {
            for (IExpression expression : repeatExpression.getExpressions()) {
                expression.accept(this);
            }
        }
    }

    @Override
    public void visitPenDown(PenDownExpression penDownExpression) {
        // do nothing.
    }

    @Override
    public void visitPenUp(PenUpExpression penUpExpression) {
        // do nothing.
    }
}
