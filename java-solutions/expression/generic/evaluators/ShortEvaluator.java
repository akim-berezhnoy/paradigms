package expression.generic.evaluators;

public class ShortEvaluator extends Evaluator<Short> {
    @Override
    public Short add(Short a, Short b) {
        return (short) (a+b);
    }

    @Override
    public Short sub(Short a, Short b) {
        return (short) (a-b);
    }

    @Override
    public Short mul(Short a, Short b) {
        return (short) (a*b);
    }

    @Override
    public Short div(Short a, Short b) {
        return (short) (a/b);
    }

    @Override
    public Short neg(Short a) {
        return (short) (-a);
    }

    @Override
    public Short abs(Short a) {
        return (short) Math.abs(a);
    }

    @Override
    public Short square(Short a) {
        return (short) (a*a);
    }

    @Override
    public Short mod(Short a, Short b) {
        return (short) (a%b);
    }

    @Override
    public Short castT(int value) {
        return (short) value;
    }
}
