package expression.generic.operations;

import expression.exceptions.CheckedAdd;
import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class GenericAdd extends CheckedAdd {
    public GenericAdd(GenericExpression e1, GenericExpression e2) {
        super(e1, e2);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.add(a, b);
    }
}
