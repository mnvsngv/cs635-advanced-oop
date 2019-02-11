package expressions;

import program.Context;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.List;


/*
 * Class to represent repeat expressions like:
 * repeat 3
 *     move 10
 *     turn 90
 * end
 *
 * The expression contains the expressions to repeat and one
 * ITerminalExpression child.
 * The ITerminalExpression node is used to get the number of times to repeat
 * the expressions.
 *
 * Interpreting this returns the cumulative value of the expressions
 * multiplied by the number of iterations.
 */
public class RepeatExpression implements IExpression {

    private ITerminalExpression numberOfRepetitionsExpression;
    private List<IExpression> expressions = new ArrayList<>();

    public RepeatExpression(ITerminalExpression numberOfRepetitions) {
        this.numberOfRepetitionsExpression = numberOfRepetitions;
    }


    public ITerminalExpression getNumberOfRepetitionsExpression() {
        return numberOfRepetitionsExpression;
    }

    public List<IExpression> getExpressions() {
        return expressions;
    }


    @Override
    public Double interpret(Context context) {
        double value = 0;
        int numberOfRepetitions =
                (int) numberOfRepetitionsExpression.interpret(context)
                        .doubleValue();

        for(int i = 0; i < numberOfRepetitions; i++) {
            for (IExpression expression : expressions) {
                value += expression.interpret(context);
            }
        }

        return value;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visitRepeat(this);
    }


    public void addExpression(IExpression expression) {
        expressions.add(expression);
    }
}
