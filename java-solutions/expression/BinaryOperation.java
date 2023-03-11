package expression;

import expression.generic.evaluators.*;
import expression.generic.GenericExpression;

public abstract class BinaryOperation implements GenericExpression {
    private final GenericExpression leftOperand;
    private final GenericExpression rightOperand;

    public BinaryOperation(GenericExpression leftOperand, GenericExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public abstract String getSign();
    public abstract int getPriority();

    @Override
    final public int evaluate(int x, int y, int z) {
        return evaluateGeneric(new IntegerEvaluator().setChecks(true), x, y, z);
    }

    @Override
    final public <T> T evaluateGeneric(Evaluator<T> evaluator, T x, T y, T z) {
        return evaluateImpl(evaluator,
                leftOperand.evaluateGeneric(evaluator, x, y ,z),
                rightOperand.evaluateGeneric(evaluator, x, y, z));
    }

    protected abstract <T> T evaluateImpl(Evaluator<T> evaluator, T a, T b);

    @Override
    public int hashCode() {
        return (((this.getClass().hashCode()) +
                leftOperand.hashCode()) * 7) +
                rightOperand.hashCode() * 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final BinaryOperation that) {
            return this.leftOperand.equals(that.leftOperand) &&
                    this.rightOperand.equals(that.rightOperand)&&
                    that.getClass().equals(this.getClass());
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + getSign() + " " + rightOperand + ")";
    }


    @Override
    public String toMiniString() {
        boolean leftBrackets = leftOperand.getPriority() > this.getPriority();
        boolean rightBrackets = rightOperand.getPriority() > this.getPriority();
        if (rightOperand.getPriority() == this.getPriority() && (!this.isRightAssociative() || !rightOperand.isLeftAssociative())) {
            rightBrackets = true;
        }
        return (leftBrackets ? '(' : "") + leftOperand.toMiniString() + (leftBrackets ? ')' : "") +
                ' ' + getSign() + ' ' +
                (rightBrackets ? '(' : "") + rightOperand.toMiniString() + (rightBrackets ? ')' : "");
    }
}
