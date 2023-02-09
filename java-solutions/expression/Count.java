package expression;

public class Count extends UnaryOperation {
    public Count(Express operand) {
        super(operand);
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
    public String getSign() {
        return "count";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public int makeOperation(int a) {
        int c = 0;
        for (int i = 0; i < 32; i++) {
            if (((a >> i) & 1) == 1) {
                c++;
            }
        }
        return c;
    }
}
