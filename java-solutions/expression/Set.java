package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class Set extends BinaryOperation {
    public Set(GenericExpression leftOperand, GenericExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.set(a, b);
    }

    @Override
    public String getSign() {
        return "set";
    }

    @Override
    public int getPriority() {
        return priority();
    }

    public static int priority() {
        return 10;
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }
}
