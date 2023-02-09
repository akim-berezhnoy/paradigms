package expression.exceptions;

import expression.*;

public class Main {
    public static void main(String[] args) {
        try {
            TripleExpression expr = new ExpressionParser().parse("1000000*x*x*x*x*x/(x-1)");
            for (int i = 0; i < 11; i++) {
                try {
                    System.out.println(expr.evaluate(i, 0, 0));
                } catch (EvaluationException e) {
                    System.out.println("EvaluationException: " + e.getMessage());
                }
            }
        } catch (ParseException e) {
            System.out.println("ParseException: " + e.getMessage());
        }
    }
}
