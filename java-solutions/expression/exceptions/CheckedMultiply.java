package expression.exceptions;

import expression.Express;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(Express e1, Express e2) {
        super(e1, e2);
    }

    @Override
    public int makeOperation(int a, int b) {
        if (a > 0 && b > 0 && a > Integer.MAX_VALUE/b || a < 0 && b < 0 && a < Integer.MAX_VALUE/b) {
            throw new ResultOverflowException("Result overflow: %d %s %d > %d".formatted(a, super.getSign(), b, Integer.MAX_VALUE));
        }
        if (b != -1 && a > 0 && b < 0 && a > Integer.MIN_VALUE/b || a < 0 && b > 0 && a < Integer.MIN_VALUE/b) {
            throw new ResultOverflowException("Result overflow: %d %S %d < %d".formatted(a, super.getSign(), b, Integer.MIN_VALUE));
        }
        return super.makeOperation(a, b);
    }
}
