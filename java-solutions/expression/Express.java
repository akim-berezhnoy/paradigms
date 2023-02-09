package expression;

public interface Express extends Expression, TripleExpression, ToMiniString {
    int getPriority();
    boolean isLeftAssociative();
    boolean isRightAssociative();
}
