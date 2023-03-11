package expression.generic;

import expression.Express;
import expression.generic.evaluators.Evaluator;

public interface GenericExpression extends Express {
    default <T> T evaluateGeneric(Evaluator<T> evaluator, T x, T y, T z) {
        throw new UnsupportedOperationException();
    }
}
