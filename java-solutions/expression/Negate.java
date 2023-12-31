package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public class Negate extends UnaryOperation {
    public Negate(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.setChecks(false).neg(a);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
