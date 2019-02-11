package expressions;

import program.Context;
import visitors.IVisitor;


/*
 * Class to represent move expressions like:
 * turn 90
 *
 * The expression one ITerminalExpression child.
 * The child node is used to get how much to turn.
 *
 * Interpreting this returns the value of how much the turtle turned.
 */
public class TurnExpression implements IExpression {

    private ITerminalExpression valueExpression;


    public TurnExpression(ITerminalExpression valueExpression) {
        this.valueExpression = valueExpression;
    }


    public ITerminalExpression getValueExpression() {
        return valueExpression;
    }


    @Override
    public Double interpret(Context context) {
        double degreesToTurn = valueExpression.interpret(context);
        context.getTurtle().turn((int) degreesToTurn);

        return degreesToTurn;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitTurn(this);
    }
}
