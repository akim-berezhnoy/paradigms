package expression.exceptions;

import expression.Add;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.setChecks(true).add(a,b);
    }
}
