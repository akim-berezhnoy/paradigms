package expression.generic.operations;

import expression.Set;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class GenericSet extends Set {
    public GenericSet(GenericExpression leftOperand, GenericExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.set(a,b);
    }
}
