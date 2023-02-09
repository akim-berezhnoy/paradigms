package expression;

import expression.exceptions.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        System.out.println(new ExpressionParser().parse("countx").evaluate(1,1,1));
    }
}