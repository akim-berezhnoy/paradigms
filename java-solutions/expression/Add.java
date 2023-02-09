package expression;

public class Add extends BinaryOperation {
    public Add(Express e1, Express e2) {
        super(e1, e2);
    }

    @Override
    public String getSign() {
        return "+";
    }

    public static int priority() {
        return 4;
    }

    @Override
    public int getPriority() {
        return 4;
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
        return a + b;
    }
}
