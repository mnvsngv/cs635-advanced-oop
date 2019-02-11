package expressions;

import program.Context;
import visitors.IVisitor;


/*
 * Expression to evaluate constant values.
 * Used as terminal/leaf nodes.
 *
 * Interpreting this returns the constant value.
 */
public class ConstantExpression implements ITerminalExpression {

    private double value;


    public ConstantExpression(double value) {
        this.value = value;
    }


    @Override
    public Double interpret(Context context) {
        return value;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitConstant(this);
    }
}
