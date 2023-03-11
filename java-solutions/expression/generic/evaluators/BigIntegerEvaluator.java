package expression.generic.evaluators;

import java.math.BigInteger;

public class BigIntegerEvaluator extends Evaluator<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger sub(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger mul(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger div(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger neg(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger abs(BigInteger a) {
        return a.abs();
    }

    @Override
    public BigInteger square(BigInteger a) {
        return a.pow(2);
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) {
        return a.mod(b);
    }

    @Override
    public BigInteger castT(int value) {
        return BigInteger.valueOf(value);
    }
}
