package expression.generic.evaluators;

public class LongEvaluator extends Evaluator<Long> {
    @Override
    public Long add(Long a, Long b) {
        return a+b;
    }

    @Override
    public Long sub(Long a, Long b) {
        return a-b;
    }

    @Override
    public Long mul(Long a, Long b) {
        return a*b;
    }

    @Override
    public Long div(Long a, Long b) {
        return a/b;
    }

    @Override
    public Long neg(Long a) {
        return -a;
    }

    @Override
    public Long abs(Long a) {
        return Math.abs(a);
    }

    @Override
    public Long square(Long a) {
        return a*a;
    }

    @Override
    public Long mod(Long a, Long b) {
        return a%b;
    }

    @Override
    public Long castT(int value) {
        return (long) value;
    }
}
