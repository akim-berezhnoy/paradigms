package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class Count extends UnaryOperation {
    public Count(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.count(a);
    }

    @Override
    public String getSign() {
        return "count";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
