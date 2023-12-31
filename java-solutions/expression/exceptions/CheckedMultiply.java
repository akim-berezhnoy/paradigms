package expression.exceptions;

import expression.Multiply;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.setChecks(true).mul(a, b);
    }
}
