package expressions;

import program.Context;
import visitors.IVisitor;


/*
 * Class to represent penDown expressions like:
 * penDown
 *
 * Interpreting this returns 0.
 */
public class PenDownExpression implements IExpression {

    @Override
    public Double interpret(Context context) {
        context.getTurtle().penDown();
        return 0.0;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitPenDown(this);
    }
}
