package expression.generic.operations;

import expression.Subtract;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class GenericSub extends Subtract {
    public GenericSub(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.sub(a,b);
    }
}
