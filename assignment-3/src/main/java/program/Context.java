package program;

import models.Turtle;

import java.util.HashMap;
import java.util.Map;


/*
 * Context needed by the expressions to interpret. Contains variables and an
 * instance of a turtle, along with methods to access the context data.
 */
public class Context {
    private Map<String, Double> variables = new HashMap<>();
    private Turtle turtle;


    public Context(Turtle turtle) {
        this.turtle = turtle;
    }


    public Double getVariableValue(String name) {
        return variables.get(name);
    }

    public void setVariableValue(String name, double value) {
        variables.put(name, value);
    }

    public Turtle getTurtle() {
        return turtle;
    }
}
