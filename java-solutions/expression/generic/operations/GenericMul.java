package expression.generic.operations;

import expression.Multiply;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class GenericMul extends Multiply {
    public GenericMul(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.mul(a,b);
    }
}
