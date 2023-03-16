package expression.generic;

import expression.exceptions.CheckedAdd;
import expression.exceptions.EvaluationException;
import expression.exceptions.ExpressionParser;
import expression.generic.evaluators.*;

public class GenericTabulator implements Tabulator {

    private <T> Object evaluateExpr(GenericExpression expr, Evaluator<T> evaluator, int x, int y, int z) {
        return expr.evaluateGeneric(evaluator, evaluator.castT(x), evaluator.castT(y), evaluator.castT(z));
    }

    private Object evalInMode(String mode, GenericExpression expr, int x, int y , int z) {
//        CheckedOp (= Unchecked
        // Copypaste
        return switch (mode) {
            case "i" -> evaluateExpr(expr, new IntegerEvaluator().setChecks(true), x, y, z);
            case "u" -> evaluateExpr(expr, new IntegerEvaluator().setChecks(false), x, y, z);
            case "d" -> evaluateExpr(expr, new DoubleEvaluator().setChecks(false), x, y, z);
            case "f" -> evaluateExpr(expr, new FloatEvaluator().setChecks(false), x, y, z);
            case "bi" -> evaluateExpr(expr, new BigIntegerEvaluator().setChecks(false), x, y, z);
            case "s" -> evaluateExpr(expr, new ShortEvaluator().setChecks(false), x, y, z);
            case "l" -> evaluateExpr(expr, new LongEvaluator().setChecks(false), x, y, z);
            default -> throw new UnsupportedOperationException();
        };
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] res = new Object[x2-x1+1][y2-y1+1][z2-z1+1];
        GenericExpression expr = new ExpressionParser().parse(expression);
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    Object cellValue;
                    try {
                        cellValue = evalInMode(mode, expr, x, y, z);
                    } catch (EvaluationException | ArithmeticException e){
                        cellValue = null;
                    }
                    res[x-x1][y-y1][z-z1] = cellValue;
                }
            }
        }
        return res;
    }
}
