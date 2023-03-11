package expression.generic.operations;

import expression.Count;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class GenericCount extends Count {
    public GenericCount(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.count(a);
    }
}
