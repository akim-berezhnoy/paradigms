package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

import java.util.Objects;

public class Const implements GenericExpression {
    private final int value;
    private final String stringValue;

    public Const(int value) {
        this.value = value;
        stringValue = String.valueOf(value);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return true;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateGeneric(new IntegerEvaluator().setChecks(false), x, y, z);
    }

    @Override
    public <T> T evaluateGeneric(Evaluator<T> evaluator, T x, T y, T z) {
        return evaluator.castT(value);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Const that) {
            return Objects.equals(that.value, this.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
