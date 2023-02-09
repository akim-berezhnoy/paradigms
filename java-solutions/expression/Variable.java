package expression;

public class Variable implements Express {
    private final String name;

    public Variable(String name) {
        this.name = name;
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
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Variable that) {
            return that.name.equals(this.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
