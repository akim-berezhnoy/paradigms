package expression.exceptions;

import expression.*;
import expression.parser.*;

import java.util.Set;
import java.util.Stack;

public class ExpressionParser implements TripleParser {
    public static TripleExpression parse(final CharSource source) {
        return new StaticExpressionParser(source).parseExpression();
    }
    @Override
    public TripleExpression parse(String expression) {
        return parse(new StringSource(expression));
    }
    private static class StaticExpressionParser extends BaseParser {
        private static final PrefixTree binaryTokens = new PrefixTree(
                Set.of("+", "-", "/", "*", "clear", "set", ")"));
        private static final PrefixTree unaryTokens = new PrefixTree(
                Set.of("-", "x", "y", "z", "count", "reverse", "("));

        private int bracketBalance;

        public StaticExpressionParser(CharSource source) {
            super(source);
        }

        /*
         * Main ExpressionParser method. Parses till source end or closing bracket.
         * May be executed recursively inside brackets to parse full inside block.
         */
        private Express parseExpression() {
            Stack<Express> operands = new Stack<>();
            Stack<String> operators = new Stack<>();
            operands.push(nextOperand());
            skipWhitespace(); //SKIP WHITESPACE
            while (!eof()) {
                String token = parseToken(binaryTokens);
                if (isFunction(token) &&!Character.isWhitespace(peek()) && peek() != '(' && peek() != '-') {
                    throw new WhitespaceException(getStartPos(token) + ": Expected whitespace after function " + token + '.');
                }
                skipWhitespace(); //SKIP WHITESPACE
                if (token.equals(")")) {
                    if (--bracketBalance < 0) {
                        throw new BracketBalanceException(getStartPos(token) + ": There are more closed brackets, than opened ones.");
                    }
                    break;
                }
                int currentTokenPriority = priority(token);
                while (!operators.isEmpty() && priority(operators.peek()) <= currentTokenPriority) {
                    collectDescendingOperatorsOperand(operands, operators);
                }
                operands.push(nextOperand());
                skipWhitespace(); //SKIP WHITESPACE
                operators.push(token);
            }
            while (!operators.isEmpty()) {
                collectDescendingOperatorsOperand(operands, operators);
            }
            if (eof() && bracketBalance > 0) {
                throw new BracketBalanceException("There are more opened brackets, than closed in the end of source.");
            }
            return operands.pop();
        }

        /*
         * Collects operators from the top of the operator stack and pushes created operands in the operand stack.
         *
         */

        private void collectDescendingOperatorsOperand(Stack<Express> units, Stack<String> operations) {
            do {
                units.push(createOperation(operations.pop(), units.pop(), units.pop()));
            } while (!operations.isEmpty() && priority(operations.peek()) <= units.peek().getPriority());
        }

        /*
         * Transforms an operand in expression. (Expr)
         */
        private Express nextOperand() {
            String operand = parseOperand();
            if (isNumber(operand)) {
                // Constants
                int constantValue;
                try {
                    constantValue = Integer.parseInt(operand);
                } catch (NumberFormatException e) {
                    throw new ConstantFormatException(getStartPos(operand) + ": " + operand + " is not a valid int constant.");
                }
                return new Const(constantValue);
            } else {
                return switch (operand) {
                    // Blocks
                    case "(" -> {
                        ++bracketBalance;
                        yield parseExpression();
                    }
                    // Variables
                    case "x", "y", "z" -> new Variable(operand);
                    // UnaryOperations
                    case "-", "count", "reverse" -> createOperation(operand, nextOperand());
                    //
                    // HERE
                    //
                    default -> throw new TokenParseException(getStartPos(operand) +
                            ": Expected bracket-block, variable or constant, but found " +
                            (operand.isEmpty() ? "nothing" :
                                    (binaryTokens.contains(operand) ? "binary token " + operand :
                                            operand)) + ".");
                };
            }
        }

        /*
         * Gobs next operand. (String)
         *
         * operands:
         *     signed constants
         *     bracket blocks
         *     variables
         *     operands, which were produced by applying upcoming unary operation
         */
        private String parseOperand() {
            skipWhitespace(); //SKIP WHITESPACE
            StringBuilder operand = new StringBuilder();
            if (!between('0','9')) {
                operand.append(parseToken(unaryTokens));
            }
            if (operand.toString().equals("(")) {
                return operand.toString();
            }
            takeDigits(operand);
            return operand.toString();
        }

        /*
         * Gobs next token. (String)
         */
        private String parseToken(PrefixTree grammar) {
            skipWhitespace(); //SKIP WHITESPACE
            StringBuilder token = new StringBuilder();
            while (!Character.isWhitespace(peek()) && !eof() &&
                    ((grammar.hasPrefix(token.toString() + peek())) || !grammar.contains(token.toString()))) {
                token.append(take());
            }
            return token.toString();
        }

        ////////////////////////////////////////////////////////////////////////////////

        /*
         * auxiliary methods (number checking, expressions creation, priority management)
         */

        private boolean isNumber(String s) {
            return !s.isEmpty() && Character.isDigit(s.charAt(0)) || (s.length() > 1 && Character.isDigit(s.charAt(1)));
        }

        private int priority(String operation) {
            return switch (operation) {
                case "+" -> CheckedAdd.priority();
                case "-" -> CheckedSubtract.priority();
                case "*" -> CheckedMultiply.priority();
                case "/" -> CheckedDivide.priority();
                case "clear" -> Clear.priority();
                case "set" -> expression.Set.priority();
                //
                // HERE
                //
                default -> 0;
            };
        }

        private boolean isFunction(String operation) {
            return switch (operation) {
                case "clear", "set", "count" -> true;
                default -> false;
            };
        }

        private Express createOperation(String operator, Express right, Express left) {
            return switch (operator) {
                case "+" -> new CheckedAdd(left, right);
                case "-" -> new CheckedSubtract(left, right);
                case "*" -> new CheckedMultiply(left, right);
                case "/" -> new CheckedDivide(left, right);
                case "clear" -> new Clear(left, right);
                case "set" -> new expression.Set(left, right);
                //
                // HERE
                //
                default -> throw new UnsupportedBinaryOperatorException(
                        getStartPos(operator) + ": " +
                        "Unsupported binary operator: \"" + operator + "\". " +
                        "Expected one of these: " + binaryTokens + ".");
            };
        }

        private Express createOperation(String operator, Express operand) {
            return switch (operator) {
                case "-" -> new CheckedNegate(operand);
                case "count" -> new Count(operand);
                //
                // HERE
                //
                default -> throw new UnsupportedUnaryOperatorException(
                        getStartPos(operator) + ": " +
                        "Unsupported operator: \"" + operator + "\". " +
                        "Expected one of these: " + unaryTokens + ".");
            };
        }

        private int getStartPos(String token) {
            return getPos() - token.length();
        }
    }
}
