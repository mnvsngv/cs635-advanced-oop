package expressions;

import program.Context;
import visitors.IVisitor;

/*
 * Class to represent assignment expressions like:
 * #var = 10
 *
 * The expression contains the variable name and one ConstantExpression child.
 * The child node is used to get the value of the assignment.
 *
 * Interpreting this returns the value of the constant.
 */
public class AssignmentExpression implements IExpression {

    private String variableName;
    private ConstantExpression constantExpression;


    public AssignmentExpression(String variableName,
                                ConstantExpression constantExpression) {
        this.variableName = variableName;
        this.constantExpression = constantExpression;
    }


    public String getVariableName() {
        return variableName;
    }

    public ConstantExpression getConstantExpression() {
        return constantExpression;
    }


    @Override
    public Double interpret(Context context) {
        double constantValue = constantExpression.interpret(context);
        context.setVariableValue(variableName, constantValue);
        return constantValue;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitAssignment(this);
    }
}
