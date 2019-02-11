package program;

import exceptions.ParseException;
import expressions.IExpression;
import models.Turtle;
import parsing.Lexer;
import parsing.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/*
 * Class to encapsulate the execution of a turtle program code file.
 */
public class TurtleProgram {

    private BufferedReader reader;
    private Turtle turtle = new Turtle();
    private Context context = new Context(turtle);
    private boolean isAlive = true;


    /*
     * Tries to create a program from a source code file. Searches for the
     * file in the classpath first, then tries to find it in the file system
     * if it was not found.
     */
    public TurtleProgram(String fileName) throws IOException {
        URL resourceUrl = ClassLoader.getSystemResource(fileName);
        Path filePath = null;

        if(resourceUrl != null) {
            try {
                filePath = Paths.get(resourceUrl.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            filePath = Paths.get(fileName);
        }

        reader = Files.newBufferedReader(filePath);
    }


    public Context getContext() {
        return context;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    /*
     * Executes the interpreter by interpreting the source code line by line
     */
    public void interpret() throws IOException, ParseException {
        if(!isAlive) {
            throw new RuntimeException(
                    "The program has finished executing every line!");
        }

        String line = reader.readLine();
        if(line != null) {
            parseLine(line).interpret(context);
        } else {
            isAlive = false;
        }
    }

    /*
     * Parses all the lines in the source code and returns a list of the
     * parsed expressions instead of executing them. Useful to apply visitors
      * on the source code.
     */
    public List<IExpression> getExpressions()
            throws IOException, ParseException {

        List<IExpression> expressions = new ArrayList<>();
        String line;

        while((line = reader.readLine()) != null) {
            expressions.add(parseLine(line));
        }

        return expressions;
    }


    private IExpression parseLine(String line)
            throws IOException, ParseException {
        try {
            return Parser.parse(Lexer.tokenize(line), reader);
        } catch (ParseException e) {
            throw new ParseException("Error parsing line: " + line, e);
        }
    }

}
