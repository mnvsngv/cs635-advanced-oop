package expressions;

import program.Context;
import visitors.IVisitor;


/*
 * Class to represent penUp expressions like:
 * penUp
 *
 * Interpreting this returns 0.
 */
public class PenUpExpression implements IExpression {

    @Override
    public Double interpret(Context context) {
        context.getTurtle().penUp();
        return 0.0;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitPenUp(this);
    }
}
