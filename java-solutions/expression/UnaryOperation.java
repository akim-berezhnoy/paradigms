package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public abstract class UnaryOperation implements GenericExpression {

    private final GenericExpression operand;

    public UnaryOperation(GenericExpression operand) {
        this.operand = operand;
    }

    public abstract String getSign();
    public abstract int getPriority();

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return true;
    }

    @Override
    final public int evaluate(int x, int y, int z) {
        return evaluateGeneric(new IntegerEvaluator().setChecks(true), x, y, z);
    }

    @Override
    final public <T> T evaluateGeneric(Evaluator<T> evaluator, T x, T y, T z) {
        return evaluateImpl(evaluator,
                operand.evaluateGeneric(evaluator, x, y ,z));
    }

    protected abstract <T> T evaluateImpl(Evaluator<T> evaluator, T a);

    @Override
    public int hashCode() {
        return (((this.getClass().hashCode()) +
                operand.hashCode()) * 7);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final UnaryOperation that) {
            return this.operand.equals(that.operand) &&
                    that.getClass().equals(this.getClass());
        }
        return false;
    }


    @Override
    public String toString() {
        return getSign() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        if (operand.getPriority() > getPriority()) {
            return getSign() + "(" + operand.toMiniString() + ")";
        } else {
            return getSign() + " " + operand.toMiniString();
        }
    }
}
