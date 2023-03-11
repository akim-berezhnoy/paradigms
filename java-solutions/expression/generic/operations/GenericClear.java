package expression.generic.operations;

import expression.Clear;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class GenericClear extends Clear {
    public GenericClear(GenericExpression leftOperand, GenericExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.clear(a,b);
    }
}
