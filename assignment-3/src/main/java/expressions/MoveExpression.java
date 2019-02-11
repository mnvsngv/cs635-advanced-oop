package expressions;

import program.Context;
import visitors.IVisitor;

/*
 * Class to represent move expressions like:
 * move 10
 *
 * The expression one ITerminalExpression child.
 * The child node is used to get how much to move.
 *
 * Interpreting this returns the value of the distance moved.
 */
public class MoveExpression implements IExpression {

    private ITerminalExpression valueExpression;


    public MoveExpression(ITerminalExpression valueExpression) {
        this.valueExpression = valueExpression;
    }


    public ITerminalExpression getValueExpression() {
        return valueExpression;
    }


    @Override
    public Double interpret(Context context) {
        double distance = valueExpression.interpret(context);
        context.getTurtle().move(distance);

        return distance;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitMove(this);
    }
}
