package expression.generic.operations;

import expression.UnaryOperation;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class GenericAbs extends UnaryOperation {
    public GenericAbs(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.abs(a);
    }

    @Override
    public String getSign() {
        return "abs";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
