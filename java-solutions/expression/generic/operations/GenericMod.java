package expression.generic.operations;

import expression.BinaryOperation;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class GenericMod extends BinaryOperation {

    public GenericMod(GenericExpression leftOperand, GenericExpression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b) {
        return evaluator.mod(a, b);
    }

    @Override
    public String getSign() {
        return "mod";
    }

    @Override
    public int getPriority() {
        return priority();
    }

    public static int priority() {
        return 3;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return true;
    }
}
