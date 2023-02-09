package expression;

public abstract class BinaryOperation implements Express {
    private final Express leftOperand;
    private final Express rightOperand;

    public BinaryOperation(Express leftOperand, Express rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public abstract String getSign();
    public abstract int getPriority();

    public abstract int makeOperation(int a, int b);

    @Override
    public int evaluate(int x) {
        return makeOperation(leftOperand.evaluate(x), rightOperand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(leftOperand.evaluate(x,y,z), rightOperand.evaluate(x,y,z));
    }

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
