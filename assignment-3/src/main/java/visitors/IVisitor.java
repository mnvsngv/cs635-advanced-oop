package visitors;

import expressions.*;


/*
 * Interface to visit Expression nodes.
 */
public interface IVisitor {
    void visitAssignment(AssignmentExpression assignmentExpression);
    void visitConstant(ConstantExpression constantExpression);
    void visitVariable(VariableExpression variableExpression);
    void visitMove(MoveExpression moveExpression);
    void visitTurn(TurnExpression turnExpression);
    void visitRepeat(RepeatExpression repeatExpression);
    void visitPenDown(PenDownExpression penDownExpression);
    void visitPenUp(PenUpExpression penUpExpression);
}
