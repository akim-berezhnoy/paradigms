package expression.generic.operations;

import expression.UnaryOperation;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class GenericSquare extends UnaryOperation {
    public GenericSquare(GenericExpression operand) {
        super(operand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a) {
        return evaluator.square(a);
    }

    @Override
    public String getSign() {
        return "square";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
