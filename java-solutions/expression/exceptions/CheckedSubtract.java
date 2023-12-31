package expression.exceptions;

import expression.Subtract;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.setChecks(true).sub(a, b);
    }
}
