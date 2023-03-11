package expression.generic.evaluators;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.ResultOverflowException;

public class IntegerEvaluator extends Evaluator<Integer> {

    @Override
    public Integer add(Integer a, Integer b) {
        if (checked) {
            if (a > 0 && b > 0 && a > Integer.MAX_VALUE - b) {
                throw new ResultOverflowException(String.format("Result overflow: %d + %d > %d", a, b, Integer.MAX_VALUE));
            }
            if (a < 0 && b < 0 && a < Integer.MIN_VALUE - b) {
                throw new ResultOverflowException(String.format("Result overflow: %d + %d < %d", a, b, Integer.MIN_VALUE));
            }
        }
        return a+b;
    }

    @Override
    public Integer sub(Integer a, Integer b) {
        if (checked) {
            if (a >= 0 && b < 0 && a > Integer.MAX_VALUE + b) {
                throw new ResultOverflowException(String.format("Result overflow: %d - %d > %d", a, b, Integer.MAX_VALUE));
            }
            if (a < 0 && b > 0 && a < Integer.MIN_VALUE + b) {
                throw new ResultOverflowException(String.format("Result overflow: %d - %d < %d", a, b, Integer.MIN_VALUE));
            }
        }
        return a - b;
    }

    @Override
    public Integer mul(Integer a, Integer b) {
        if (checked) {
            if (a > 0 && b > 0 && a > Integer.MAX_VALUE / b || a < 0 && b < 0 && a < Integer.MAX_VALUE / b) {
                throw new ResultOverflowException("Result overflow: %d * %d > %d".formatted(a, b, Integer.MAX_VALUE));
            }
            if (b != -1 && a > 0 && b < 0 && a > Integer.MIN_VALUE / b || a < 0 && b > 0 && a < Integer.MIN_VALUE / b) {
                throw new ResultOverflowException("Result overflow: %d * %d < %d".formatted(a, b, Integer.MIN_VALUE));
            }
        }
        return a * b;
    }

    @Override
    public Integer div(Integer a, Integer b) {
        if (b == 0) {
            throw new DivisionByZeroException("Forbidden operation: %d / %d".formatted(a, b));
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ResultOverflowException("Result overflow: %d / %d > %d".formatted(a, b, Integer.MAX_VALUE));
        }
        return a / b;
    }

    @Override
    public Integer neg(Integer a) {
        if (checked) {
            if (a == Integer.MIN_VALUE) {
                throw new ResultOverflowException(String.format("Result overflow: -(%d) > %d", a, Integer.MAX_VALUE));
            }
        }
        return -a;
    }

    @Override
    public Integer abs(Integer a) {
        if (checked) {
            if (a == Integer.MIN_VALUE) {
                throw new ResultOverflowException(String.format("Result overflow: abs(%d) > %d", a, Integer.MAX_VALUE));
            }
        }
        return Math.abs(a);
    }

    @Override
    public Integer square(Integer a) {
        if (checked) {
            if (abs(a) > Math.sqrt(Integer.MAX_VALUE)) {
                throw new ResultOverflowException(String.format("Result overflow: abs(%d) > %d", a, Integer.MAX_VALUE));
            }
        }
        return a*a;
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        return a % b;
    }

    @Override
    public Integer set(Integer a, Integer b) {
//        if (checked) {
//            if (b > 31 || b < 0) {
//                throw new IntIndexOutOfBoundsException("Bit index %d is out of bounds of 32-bit int number.".formatted(b));
//            }
//        }
        return a | (1<<b);
    }

    @Override
    public Integer clear(Integer a, Integer b) {
//        if (checked) {
//            if (b > 31 || b < 0) {
//                throw new IntIndexOutOfBoundsException("Bit index %d is out of bounds of 32-bit int number.".formatted(b));
//            }
//        }
        return a & ~(1<<b);
    }

    @Override
    public Integer count(Integer a) {
        int c = 0;
        for (int i = 0; i < 32; i++) {
            if (((a >> i) & 1) == 1) {
                c++;
            }
        }
        return c;
    }

    @Override
    public Integer castT(int value) {
        return value;
    }
}
