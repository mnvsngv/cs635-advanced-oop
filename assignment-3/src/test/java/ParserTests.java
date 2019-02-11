import exceptions.ParseException;
import expressions.*;
import models.Turtle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsing.Lexer;
import parsing.Parser;
import program.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/*
 * Tests for the Lexer and Parser classes.
 */
class ParserTests {

    private final String VARIABLE_NAME = "var";
    private final Double CONSTANT_VALUE = 10.0;
    private final String VARIABLE_ASSIGNMENT = "#var = 10";
    private final String MOVE_CONSTANT = "move 10";
    private final String MOVE_VARIABLE = "move #var";
    private final String TURN_CONSTANT = "turn 10";
    private final String TURN_VARIABLE = "turn #var";
    private final String PEN_UP = "penUp";
    private final String PEN_DOWN = "penDown";

    private final String REPEAT_CONSTANT_LINE = "repeat 10";
    private final String REPEAT_CONSTANT_BLOCK =
            MOVE_CONSTANT + "\n" +
            TURN_CONSTANT + "\n" +
            "end";

    private final String REPEAT_VARIABLE_LINE = "repeat #var";
    private final String REPEAT_VARIABLE_BLOCK =
            MOVE_VARIABLE + "\n" +
            TURN_VARIABLE + "\n" +
            "end";

    private Context context;


    @BeforeEach
    void initialize() {
        context = new Context(new Turtle());
    }


    @Test
    void constantTest() {
        ConstantExpression constantExpression =
                new ConstantExpression(CONSTANT_VALUE);

        assertEquals(CONSTANT_VALUE, constantExpression.interpret(context));
    }

    @Test
    void variableTest() {
        VariableExpression variableExpression =
                new VariableExpression(VARIABLE_NAME);
        context.setVariableValue(VARIABLE_NAME, CONSTANT_VALUE);

        assertEquals(VARIABLE_NAME, variableExpression.getName());
        assertEquals(CONSTANT_VALUE, variableExpression.interpret(context));
    }

    @Test
    void nonExistentVariableTest() {
        VariableExpression variableExpression =
                new VariableExpression(VARIABLE_NAME);
        context.setVariableValue("UnknownVariable", CONSTANT_VALUE);

        assertNull(variableExpression.interpret(context));
    }

    @Test
    void tokenizeAssignmentTest() {
        List<String> tokens = Lexer.tokenize(VARIABLE_ASSIGNMENT);

        assertEquals(4, tokens.size());
        assertEquals("#var", tokens.get(0));
        assertEquals("=", tokens.get(1));
        assertEquals("10", tokens.get(2));
        assertEquals("\n", tokens.get(3));
    }

    @Test
    void parseAssignmentTest() throws IOException, ParseException {
        List<String> tokens = Lexer.tokenize(VARIABLE_ASSIGNMENT);

        IExpression expression = Parser.parse(tokens, null);
        assertTrue(expression instanceof AssignmentExpression);

        AssignmentExpression assignmentExpression =
                (AssignmentExpression) expression;

        assertEquals(VARIABLE_NAME, assignmentExpression.getVariableName());
        assertEquals(CONSTANT_VALUE,
                assignmentExpression.getConstantExpression()
                        .interpret(context));
        assertEquals(CONSTANT_VALUE, assignmentExpression.interpret(context));
    }

    @Test
    void tokenizeMoveConstantTest() {
        List<String> tokens = Lexer.tokenize(MOVE_CONSTANT);

        assertEquals(3, tokens.size());
        assertEquals("move", tokens.get(0));
        assertEquals(String.valueOf(CONSTANT_VALUE.intValue()), tokens.get(1));
        assertEquals("\n", tokens.get(2));
    }

    @Test
    void parseMoveConstantTest() throws IOException, ParseException {
        moveExpressionTest(MOVE_CONSTANT);
    }

    @Test
    void tokenizeMoveVariableTest() {
        List<String> tokens = Lexer.tokenize(MOVE_VARIABLE);

        assertEquals(3, tokens.size());
        assertEquals("move", tokens.get(0));
        assertEquals("#" + VARIABLE_NAME, tokens.get(1));
        assertEquals("\n", tokens.get(2));
    }

    @Test
    void parseMoveVariableTest() throws IOException, ParseException {
        context.setVariableValue(VARIABLE_NAME, CONSTANT_VALUE);
        moveExpressionTest(MOVE_VARIABLE);
    }

    private void moveExpressionTest(String moveExpressionString)
            throws ParseException, IOException {
        List<String> tokens = Lexer.tokenize(moveExpressionString);

        IExpression expression = Parser.parse(tokens, null);
        assertMoveExpression(expression);
    }

    private void assertMoveExpression(IExpression expression) {
        assertTrue(expression instanceof MoveExpression);

        MoveExpression moveExpression = (MoveExpression) expression;

        assertEquals(CONSTANT_VALUE, moveExpression.interpret(context));
        assertEquals(CONSTANT_VALUE,
                moveExpression.getValueExpression().interpret(context));
    }

    @Test
    void tokenizeTurnConstantTest() {
        List<String> tokens = Lexer.tokenize(TURN_CONSTANT);

        assertEquals(3, tokens.size());
        assertEquals("turn", tokens.get(0));
        assertEquals(String.valueOf(CONSTANT_VALUE.intValue()), tokens.get(1));
        assertEquals("\n", tokens.get(2));
    }

    @Test
    void parseTurnConstantTest() throws IOException, ParseException {
        turnExpressionTest(TURN_CONSTANT);
    }

    @Test
    void tokenizeTurnVariableTest() {
        List<String> tokens = Lexer.tokenize(TURN_VARIABLE);

        assertEquals(3, tokens.size());
        assertEquals("turn", tokens.get(0));
        assertEquals("#" + VARIABLE_NAME, tokens.get(1));
        assertEquals("\n", tokens.get(2));
    }

    @Test
    void parseTurnVariableTest() throws IOException, ParseException {
        context.setVariableValue(VARIABLE_NAME, CONSTANT_VALUE);
        turnExpressionTest(TURN_VARIABLE);
    }

    private void turnExpressionTest(String turnExpressionString)
            throws ParseException, IOException {
        List<String> tokens = Lexer.tokenize(turnExpressionString);

        IExpression expression = Parser.parse(tokens, null);
        assertTurnExpression(expression);
    }

    private void assertTurnExpression(IExpression expression) {
        assertTrue(expression instanceof TurnExpression);

        TurnExpression turnExpression = (TurnExpression) expression;

        assertEquals(CONSTANT_VALUE, turnExpression.interpret(context));
        assertEquals(CONSTANT_VALUE,
                turnExpression.getValueExpression().interpret(context));
    }

    @Test
    void tokenizePenUpTest() {
        List<String> tokens = Lexer.tokenize(PEN_UP);

        assertEquals("penUp", tokens.get(0));
        assertEquals("\n", tokens.get(1));
    }

    @Test
    void parsePenUpTest() throws IOException, ParseException {
        List<String> tokens = Lexer.tokenize(PEN_UP);

        IExpression expression = Parser.parse(tokens, null);

        assertTrue(expression instanceof PenUpExpression);
        PenUpExpression penUpExpression = (PenUpExpression) expression;
        penUpExpression.interpret(context);
        assertTrue(context.getTurtle().isPenUp());
    }

    @Test
    void tokenizePenDownTest() {
        List<String> tokens = Lexer.tokenize(PEN_DOWN);

        assertEquals("penDown", tokens.get(0));
        assertEquals("\n", tokens.get(1));
    }

    @Test
    void parsePenDownTest() throws IOException, ParseException {
        List<String> tokens = Lexer.tokenize(PEN_DOWN);

        IExpression expression = Parser.parse(tokens, null);

        assertTrue(expression instanceof PenDownExpression);
        PenDownExpression penDownExpression = (PenDownExpression) expression;
        penDownExpression.interpret(context);
        assertFalse(context.getTurtle().isPenUp());
    }

    @Test
    void tokenizeRepeatConstantTest() {
        List<String> tokens = Lexer.tokenize(REPEAT_CONSTANT_LINE);

        assertEquals(3, tokens.size());
        assertEquals("repeat", tokens.get(0));
        assertEquals(String.valueOf(CONSTANT_VALUE.intValue()), tokens.get(1));
        assertEquals("\n", tokens.get(2));
    }

    @Test
    void parseRepeatConstantTest() throws IOException, ParseException {
        repeatExpressionTest(REPEAT_CONSTANT_LINE,
                makeReaderFor(REPEAT_CONSTANT_BLOCK));
    }

    @Test
    void tokenizeRepeatVariableTest() {
        List<String> tokens = Lexer.tokenize(REPEAT_VARIABLE_LINE);

        assertEquals(3, tokens.size());
        assertEquals("repeat", tokens.get(0));
        assertEquals("#" + VARIABLE_NAME, tokens.get(1));
        assertEquals("\n", tokens.get(2));
    }

    @Test
    void parseRepeatVariableTest() throws IOException, ParseException {
        context.setVariableValue(VARIABLE_NAME, CONSTANT_VALUE);
        repeatExpressionTest(REPEAT_VARIABLE_LINE,
                makeReaderFor(REPEAT_VARIABLE_BLOCK));
    }

    private void repeatExpressionTest(String repeatLine,
                                      BufferedReader reader)
            throws IOException, ParseException {

        List<String> tokens = Lexer.tokenize(repeatLine);

        IExpression expression = Parser.parse(tokens, reader);
        assertTrue(expression instanceof RepeatExpression);

        RepeatExpression repeatExpression = (RepeatExpression) expression;

        Double numberOfRepetitions =
                repeatExpression.getNumberOfRepetitionsExpression()
                        .interpret(context);
        assertEquals(CONSTANT_VALUE, numberOfRepetitions);

        List<IExpression> expressions = repeatExpression.getExpressions();
        assertMoveExpression(expressions.get(0));
        assertTurnExpression(expressions.get(1));

        Double expectedFinalValue =
                (CONSTANT_VALUE + CONSTANT_VALUE) * numberOfRepetitions;

        assertEquals(expectedFinalValue, repeatExpression.interpret(context));
    }

    private BufferedReader makeReaderFor(String expression) {
        return new BufferedReader(new StringReader(expression));
    }

    @Test
    void parseInvalidTokenTest() {
        parseInvalidLineTest("invalid 10");
    }

    @Test
    void parseInvalidVariableAssignmentTest() {
        parseInvalidLineTest("#1var = 10");
    }

    @Test
    void parseInvalidTokenAssignmentTest() {
        parseInvalidLineTest("#var is = 10");
    }

    @Test
    void parseInvalidAssignmentWithoutEqualsTest() {
        parseInvalidLineTest("#var");
    }

    @Test
    void parseInvalidConstantAssignmentTest() {
        parseInvalidLineTest("#var = something");
    }

    @Test
    void parseInvalidAssignmentExtraCharactersTest() {
        parseInvalidLineTest("#var = 10 abc");
    }

    @Test
    void parseInvalidMoveNoValueTest() {
        parseInvalidLineTest("move");
    }

    @Test
    void parseInvalidMoveExtraCharactersTest() {
        parseInvalidLineTest("move 10 abc");
    }

    @Test
    void parseInvalidTurnNoValueTest() {
        parseInvalidLineTest("turn");
    }

    @Test
    void parseInvalidTurnExtraCharactersTest() {
        parseInvalidLineTest("turn 10 abc");
    }

    @Test
    void parseInvalidPenUpTest() {
        parseInvalidLineTest("penUp abc");
    }

    @Test
    void parseInvalidPenDownTest() {
        parseInvalidLineTest("penDown abc");
    }

    @Test
    void parseInvalidRepeatMissingRepetitionsTest() {
        parseInvalidLineTest("repeat");
    }

    @Test
    void parseInvalidRepeatExtraCharactersTest() {
        parseInvalidLineTest("repeat 10 abc");
    }

    @Test
    void parseInvalidConstantTest() {
        parseInvalidLineTest("move 10a");
    }

    @Test
    void parseInvalidVariableNameTest() {
        parseInvalidLineTest("move #10a");
    }

    private void parseInvalidLineTest(String invalidLine) {
        assertThrows(ParseException.class,
                () -> Parser.parse(Lexer.tokenize(invalidLine), null)
        );
    }
}
