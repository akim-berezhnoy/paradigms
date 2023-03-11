package expression.generic.evaluators;

public class FloatEvaluator extends Evaluator<Float> {

    @Override
    public Float add(Float a, Float b) {
        return a+b;
    }

    @Override
    public Float sub(Float a, Float b) {
        return a-b;
    }

    @Override
    public Float mul(Float a, Float b) {
        return a*b;
    }

    @Override
    public Float div(Float a, Float b) {
        return a/b;
    }

    @Override
    public Float neg(Float a) {
        return -a;
    }

    @Override
    public Float abs(Float a) {
        return Math.abs(a);
    }

    @Override
    public Float square(Float a) {
        return a*a;
    }

    @Override
    public Float mod(Float a, Float b) {
        return a%b;
    }

    @Override
    public Float castT(int value) {
        return (float) value;
    }
}
