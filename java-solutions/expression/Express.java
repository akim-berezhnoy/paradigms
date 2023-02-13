package expression;

public interface Express extends TripleExpression, ToMiniString {
    int getPriority();
    boolean isLeftAssociative();
    boolean isRightAssociative();
}
