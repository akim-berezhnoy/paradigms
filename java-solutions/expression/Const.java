package expression;

public class Const implements Express {
    private final int value;
    private final String stringValue;

    public Const(int value) {
        this.value = value;
        stringValue = String.valueOf(value);
    }

    @Override
    public int getPriority() {
        return 0;
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
    public int evaluate(int x) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Const that) {
            return that.value == this.value;
        }
        return false;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
