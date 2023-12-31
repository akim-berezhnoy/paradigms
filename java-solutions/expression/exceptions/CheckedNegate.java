package expression.exceptions;

import expression.Negate;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class CheckedNegate extends Negate {
    public CheckedNegate(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.setChecks(true).neg(a);
    }
}
