package visitors;

import expressions.*;
import models.Turtle;
import program.Context;

import java.util.ArrayList;
import java.util.List;


/*
 * Visitor to create a list of mementos that capture the state of the turtle
 * whenever it changes.
 *
 * NOTE: If the state of the turtle is not changed then no new memento is
 * created and added.
 * For example, calling "move 0" or "turn 0" or "penDown" if the pen is
 * already down, will have no change in the state of the turtle. So these
 * situations will not result in a new memento being added to the list of
 * returned mementos.
 */
public class MementoCreationVisitor implements IVisitor {

    private Context context;
    private List<Turtle.TurtleMemento> mementos = new ArrayList<>();


    public MementoCreationVisitor(Context context) {
        this.context = context;
    }


    public List<Turtle.TurtleMemento> getMementoList() {
        return mementos;
    }


    /*
     * Only methods where the state of the TURTLE might change are used to
     * create mementos. If there is any change to the context, that does not
     * affect the turtle and hence there is no action taken in those cases.
     */

    @Override
    public void visitAssignment(AssignmentExpression assignmentExpression) {
        // We need to update the context, in case the variable was reassigned
        assignmentExpression.interpret(context);
    }

    @Override
    public void visitConstant(ConstantExpression constantExpression) {
        // do nothing.
    }

    @Override
    public void visitVariable(VariableExpression variableExpression) {
        // do nothing.
    }

    @Override
    public void visitMove(MoveExpression moveExpression) {
        moveExpression.interpret(context);
        addMemento();
    }

    @Override
    public void visitTurn(TurnExpression turnExpression) {
        turnExpression.interpret(context);
        addMemento();
    }

    /*
     * Repeat expressions will contain multiple expressions so visit all of
     * these.
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
        penDownExpression.interpret(context);
        addMemento();
    }

    @Override
    public void visitPenUp(PenUpExpression penUpExpression) {
        penUpExpression.interpret(context);
        addMemento();
    }


    /*
     * Check if the turtle has changed its internal state, by comparing the
     * newly created memento with the latest memento in the list of mementos.
     * If the turtle has updated, then we add the new memento to the list,
     * else we ignore the new memento.
     */
    private void addMemento() {
        int lastIndex = mementos.size() - 1;
        Turtle.TurtleMemento newMemento = context.getTurtle().createMemento();

        if(lastIndex < 0 || !newMemento.equals(mementos.get(lastIndex))) {
            mementos.add(context.getTurtle().createMemento());
        }
    }
}
