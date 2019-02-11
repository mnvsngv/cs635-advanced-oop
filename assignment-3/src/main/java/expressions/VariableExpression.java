package expressions;

import program.Context;
import visitors.IVisitor;


/*
 * Expression to represent variables in expressions like:
 * move #var
 *
 * Used as terminal/leaf nodes.
 * The expression contains the variable name.
 *
 * Interpreting this returns the value of the variable.
 */
public class VariableExpression implements ITerminalExpression {

    private String name;


    public VariableExpression(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
    public Double interpret(Context context) {
        return context.getVariableValue(name);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitVariable(this);
    }
}
