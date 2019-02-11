package parsing;

import exceptions.ParseException;
import expressions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


/*
 * A parser for the following grammar:
 *
 *
 * expression := move | turn | assignment | repeat | "penUp" | "penDown"
 *
 * move := "move" value
 * turn := "turn" value
 * assignment := variable "=" (0-9)+
 * repeat := "repeat" value (expression)+ "end"
 *
 * value := (0-9)+ | variable
 *
 * variable := "#" identifier
 * identifier := (a-zA-Z)+
 *
 *
 * NOTE:
 * identifier cannot be a keyword (this is enforced by the parser)
 */
public class Parser {

    private static final String VARIABLE_REGEX = "[a-zA-Z]+";


    public static IExpression parse(List<String> tokens,BufferedReader reader)
            throws ParseException, IOException {

        // Converting to lowercase to be case insensitive
        String firstToken = tokens.get(0).toLowerCase();

        // Checking for assignment expression
        if(firstToken.startsWith("#")) {
            return assignmentExpression(tokens);
        }

        // Checking for all other tokens
        switch (firstToken) {
            case "move":
                return moveExpression(tokens);

            case "turn":
                return turnExpression(tokens);

            case "penup":
                return penUpExpression(tokens);

            case "pendown":
                return penDownExpression(tokens);

            case "repeat":
                return repeatExpression(tokens, reader);

            // Unknown token!
            default:
                throw new ParseException("Error parsing!");
        }
    }


    private static AssignmentExpression assignmentExpression(
            List<String> tokens) throws ParseException {

        // This method is only called if a token starting with # was found
        // so we know tokens.get(0) will give a result. No checks needed.
        String variableName = tokens.get(0).substring(1);


        // First token should be a valid variable identifier
        if(variableName.matches(VARIABLE_REGEX)) {

            // Next token should be "="
            if("=".equals(tokens.get(1))) {

                // Next token should be a constant
                ConstantExpression constantExpression =
                        constantExpression(tokens.get(2));

                // Ensure there are no extra characters at the end of the line
                if(!"\n".equals(tokens.get(3))) {
                    throw new ParseException("Unexpected characters in " +
                            "expression!");
                }

                return new AssignmentExpression(variableName,
                        constantExpression);
            } else {
                throw new ParseException("Incorrect assignment syntax: " +
                        "Expected '='!");
            }
        } else {
            throw new ParseException("Invalid variable name!");
        }
    }

    private static MoveExpression moveExpression(List<String> tokens)
            throws ParseException {

        // If we received only "move" then the tokens would be "move" and
        // "\n", so the value is missing
        if(tokens.size() < 3) {
            throw new ParseException("Expected value after keyword!");
        }

        // If there were extra characters after the value of the move:
        if(!"\n".equals(tokens.get(2))) {
            throw new ParseException("Unexpected character after value!");
        }

        return new MoveExpression(valueExpression(tokens.get(1)));
    }

    private static TurnExpression turnExpression(List<String> tokens)
            throws ParseException {

        // If we received only "turn" then the tokens would be "turn" and
        // "\n", so the value is missing
        if(tokens.size() < 3) {
            throw new ParseException("Expected value after keyword!");
        }

        // If there were extra characters after the value of the turn:
        if(!"\n".equals(tokens.get(2))) {
            throw new ParseException("Unexpected character after value!");
        }

        return new TurnExpression(valueExpression(tokens.get(1)));
    }

    private static PenUpExpression penUpExpression(List<String> tokens)
            throws ParseException {

        // If there were extra characters after penUp:
        if(!"\n".equals(tokens.get(1))) {
            throw new ParseException("Unexpected character after value!");
        }

        return new PenUpExpression();
    }

    private static PenDownExpression penDownExpression(List<String> tokens)
            throws ParseException{

        // If there were extra characters after penDown:
        if(!"\n".equals(tokens.get(1))) {
            throw new ParseException("Unexpected character after value!");
        }

        return new PenDownExpression();
    }

    private static RepeatExpression repeatExpression(List<String> tokens,
                                                     BufferedReader reader)
            throws IOException, ParseException {

        // We need to check if there are 3 tokens:
        // 1) REPEAT statement
        // 2) number of repetitions
        // 3) newline at the end, appended by our lexer
        if(tokens.size() < 3) {
            throw new ParseException("Missing number of repetitions!");
        }

        if(tokens.size() > 3) {
            throw new ParseException("Unexpected characters at end of line!");
        }

        ITerminalExpression numberOfRepetitionsExpression =
                valueExpression(tokens.get(1));

        RepeatExpression repeatExpression = new RepeatExpression(
                numberOfRepetitionsExpression);

        // We have to read & store all the lines till "END" inside our REPEAT
        // expression
        String line;
        while((line = reader.readLine()) != null &&
                !"end".equals(line.toLowerCase())) {
            List<String> newTokens = Lexer.tokenize(line);
            repeatExpression.addExpression(parse(newTokens, reader));
        }
        return repeatExpression;
    }

    private static ITerminalExpression valueExpression(String token)
            throws ParseException {

        ITerminalExpression terminalExpression;

        // Check if it's a variable or a constant
        if(token.startsWith("#")) {
            terminalExpression = variableExpression(token);
        } else {
            terminalExpression = constantExpression(token);
        }

        return terminalExpression;
    }

    private static VariableExpression variableExpression(String token)
            throws ParseException {

        String variableName = token.substring(1);

        if(variableName.matches(VARIABLE_REGEX)) {
            return new VariableExpression(variableName);
        } else {
            throw new ParseException("Invalid variable name!");
        }
    }

    private static ConstantExpression constantExpression(String token)
            throws ParseException {

        try {
            return new ConstantExpression(Double.valueOf(token));
        } catch (NumberFormatException e) {
            throw new ParseException("Not a valid number!", e);
        }
    }

}
