package expression.parser;

import expression.Add;
import expression.Express;
import expression.exceptions.ResultOverflowException;

public class CheckedAdd extends Add {
    public CheckedAdd(Express e1, Express e2) {
        super(e1, e2);
    }

    @Override
    public int makeOperation(int a, int b) throws ResultOverflowException {
        if (a > 0 && b > 0 && a > Integer.MAX_VALUE - b) {
            throw new ResultOverflowException(String.format("Result overflow: %d + %d > %d", a, b, Integer.MAX_VALUE));
        }
        if (a < 0 && b < 0 && a < Integer.MIN_VALUE - b) {
            throw new ResultOverflowException(String.format("Result overflow: %d + %d < %d", a, b, Integer.MIN_VALUE));
        }
        return super.makeOperation(a, b);
    }
}
