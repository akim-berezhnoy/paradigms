package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class Add extends BinaryOperation {
    public Add(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.setChecks(false).add(a,b);
    }

    @Override
    public String getSign() {
        return "+";
    }

    public static int priority() {
        return 4;
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return true;
    }
}
