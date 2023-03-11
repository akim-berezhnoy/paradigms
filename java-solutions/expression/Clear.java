package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class Clear extends BinaryOperation {
    public Clear(GenericExpression leftOperand, GenericExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.clear(a,b);
    }

    @Override
    public String getSign() {
        return "clear";
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

