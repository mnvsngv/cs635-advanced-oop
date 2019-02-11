package parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 * Class to break up an expression into its constituent tokens.
 */
public class Lexer {

    // The String REGEX is used to save the delimiter
    // The notation ?<= and ?= indicate to save the delimiter before and
    // after the regex, respectively.
    // It's then used to create the final REGEX string which uses a space
    // character and an "=" character as delimiters.
    // "=" is a delimiter for assignment expressions with no whitespace.
    private static final String REGEX = "((?<=%1$s)|(?=%1$s))";
    private static final String REGEX_WITH_DELIMITER =
            String.format(" |" + REGEX, "=");

    public static List<String> tokenize(String line) {
        String[] lineTokens = line.split(REGEX_WITH_DELIMITER);
        List<String> tokens = new ArrayList<>(Arrays.asList(lineTokens));

        // Remove empty tokens created as a result of delimiter manipulation
        tokens.removeIf(""::equals);

        // Adding a newline to help accurately check the tokens of an
        // expression and the number of tokens.
        tokens.add("\n");

        return tokens;
    }
}
