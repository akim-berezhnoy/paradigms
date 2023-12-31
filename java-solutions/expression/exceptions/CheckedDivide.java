package expression.exceptions;

import expression.Divide;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.setChecks(true).div(a, b);
    }
}
