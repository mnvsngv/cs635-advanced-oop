package expressions;

import program.Context;
import visitors.IVisitor;


/*
 * Interface to represent nodes of an expression tree.
 */
public interface IExpression {
    Double interpret(Context context);
    void accept(IVisitor visitor);
}
