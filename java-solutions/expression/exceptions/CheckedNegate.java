package expression.exceptions;

import expression.Express;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(Express operand) {
        super(operand);
    }

    @Override
    public int makeOperation(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new ResultOverflowException(String.format("Result overflow: -(%d) > %d", a, Integer.MAX_VALUE));
        }
        return super.makeOperation(a);
    }
}
