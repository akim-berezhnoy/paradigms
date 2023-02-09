package expression;

public class Multiply extends BinaryOperation {
    public Multiply(Express e1, Express e2) {
        super(e1, e2);
    }

    @Override
    public String getSign() {
        return "*";
    }

    public static int priority() {
        return 3;
    }

    @Override
    public int getPriority() {
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

    @Override
    public int makeOperation(int a, int b) {
        return a * b;
    }
}
