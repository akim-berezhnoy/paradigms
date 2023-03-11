package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class Variable implements GenericExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
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
        return evaluateT(x,y,z);
    }

    public <T> T evaluateT(T x, T y, T z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> null;
        };
    }

    @Override
    public <T> T evaluateGeneric(Evaluator<T> evaluator, T x, T y, T z) {
        return evaluateT(x,y,z);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Variable that) {
            return that.name.equals(this.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
