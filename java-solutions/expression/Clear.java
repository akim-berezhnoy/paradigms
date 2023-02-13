package expression;

public class Clear extends BinaryOperation {
    public Clear(Express leftOperand, Express rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getSign() {
        return "clear";
    }

    @Override
    public int getPriority() {
        return priority();
    }

    public static int priority() {
        return 10;
    }

    @Override
    public int makeOperation(int a, int b) {
//        if (b > 31 || b < 0) {
//            throw new IntIndexOutOfBoundsException("Bit index %d is out of bounds of 32-bit int number.".formatted(b));
//        }
        return a & ~(1<<b);
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }
}

