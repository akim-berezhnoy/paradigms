package expression.generic.operations;

import expression.Negate;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class GenericNeg extends Negate {
    public GenericNeg(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.neg(a);
    }
}
