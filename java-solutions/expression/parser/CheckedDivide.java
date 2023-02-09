package expression.parser;

import expression.Divide;
import expression.Express;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.ResultOverflowException;

public class CheckedDivide extends Divide {
    public CheckedDivide(Express e1, Express e2) {
        super(e1, e2);
    }

    @Override
    public int makeOperation(int a, int b) {
        if (b == 0) {
            throw new DivisionByZeroException("Forbidden operation: %d %s %d".formatted(a, super.getSign(), b));
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ResultOverflowException("Result overflow: %d %s %d > %d".formatted(a, super.getSign(), b, Integer.MAX_VALUE));
        }
        return super.makeOperation(a, b);
    }
}
