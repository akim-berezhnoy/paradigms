package expression;

public class Negate extends UnaryOperation {
    public Negate(Express operand) {
        super(operand);
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return true;
    }

    @Override
    public int makeOperation(int a) {
        return -a;
    }
}
