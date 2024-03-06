package h05.tree;

import h05.exception.*;
import h05.math.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to parse an expression and build a tree out of it.
 *
 * @author Nhan Huynh
 */
public final class ExpressionTreeHandler {

    /**
     * Don't let anyone instantiate this class.
     */
    private ExpressionTreeHandler() {
    }

    /**
     * Builds an arithmetic expression tree from a string recursively.
     *
     * @param expression the string representation of the arithmetic expression to parse
     * @return the root node of the arithmetic expression tree
     * @throws BadOperationException        if the iterator has no more tokens
     * @throws ParenthesesMismatchException if the parentheses are mismatched
     * @throws UndefinedOperatorException   if the operator is not defined
     */
    public static ArithmeticExpressionNode buildRecursively(Iterator<String> expression) {

        // iterator leer - Exception
        if (!expression.hasNext())
            throw new BadOperationException("No expression");


        return buildRecursivelyHelper(expression, false, false);

    }


    // wenn ein ( da ist  firstOperationStarted : wenn bereits eine öffnende klammer da ist

    public static ArithmeticExpressionNode buildRecursivelyHelper(Iterator<String> expression, boolean operationStarted, boolean firstOperationStarted) {

        if (operationStarted) {

            // case: es kam eine öffnende Klammer vor -> es muss eine operation filgen sonst wird eine UndefinedIdentifierExc geworfen
            String nextExpression = expression.next();

            switch (nextExpression) {
                case "+" -> {
                    return buildOperands(expression, Operator.ADD, null, null, 0);
                }
                case "-" -> {
                    return buildOperands(expression, Operator.SUB, null, null, 0);
                }
                case "*" -> {
                    return buildOperands(expression, Operator.MUL, null, null, 0);
                }
                case "/" -> {
                    return buildOperands(expression, Operator.DIV, null, null, 0);
                }
                case "exp" -> {
                    return buildOperands(expression, Operator.EXP, null, null, 0);
                }
                case "expt" -> {
                    return buildOperands(expression, Operator.EXPT, null, null, 0);
                }
                case "ln" -> {
                    return buildOperands(expression, Operator.LN, null, null, 0);
                }
                case "log" -> {
                    return buildOperands(expression, Operator.LOG, null, null, 0);
                }
                case "sqrt" -> {
                    return buildOperands(expression, Operator.SQRT, null, null, 0);
                }
                default -> throw new UndefinedOperatorException(nextExpression);
            }
        }


        // es hat keine operation gestartet
        String nextExpression = expression.next();

        // es ist ein literal ganz am Anfang
        if (isLiteral(nextExpression) && !expression.hasNext()) {

            String[] splitted = nextExpression.split("\\.");

            //real;
            if (splitted.length == 2) {

                return new LiteralExpressionNode(new MyRational(new Rational(new BigInteger(String.valueOf(splitted[0])), new BigInteger(String.valueOf(splitted[1])))));
            }


            String[] splitted2 = nextExpression.split("\\.");
            // real
            if (splitted2.length == 2) {

                return new LiteralExpressionNode(new MyReal(new BigDecimal(String.valueOf(nextExpression))));
            }


            return new LiteralExpressionNode(new MyInteger(new BigInteger(nextExpression)));

        }


        if (isIdentifier(nextExpression) && !expression.hasNext()) {

            return new IdentifierExpressionNode(nextExpression);
        }


        if (nextExpression.equals(ArithmeticExpressionNode.LEFT_BRACKET)) {


            return buildRecursivelyHelper(expression, true, true);
        } else {
            throw new ParenthesesMismatchException();
        }


    }


    public static ArithmeticExpressionNode buildOperands(Iterator<String> expression, Operator operator, ListItem<ArithmeticExpressionNode> head, ListItem<ArithmeticExpressionNode> tail,
                                                         int operandCount) {

        if (!expression.hasNext())
            throw new ParenthesesMismatchException();

        String nextExpression = expression.next();

        // es ist ein Literal vorhanden
        if (isLiteral(nextExpression)) {

            MyNumber myNumber = null;

            String[] splitted = nextExpression.split("/");
            String[] splitted2 = nextExpression.split("\\.");

            //real;
            if (splitted.length == 2) {

                myNumber = new MyRational(new Rational(new BigInteger(String.valueOf(splitted[0])), new BigInteger(String.valueOf(splitted[1]))));
            }

            // real
            else if (splitted2.length == 2) {

                myNumber = new MyReal(new BigDecimal(nextExpression));
            } else {
                myNumber = new MyInteger(new BigInteger(nextExpression));
            }

            if (head == null) {

                head = tail = new ListItem<ArithmeticExpressionNode>();
                tail.key = new LiteralExpressionNode(myNumber);
            } else {
                tail.next = new ListItem<ArithmeticExpressionNode>();
                tail = tail.next;
                tail.key = new LiteralExpressionNode(myNumber);
            }

            buildOperands(expression, operator, head, tail, operandCount + 1);

        }

        // es ist ein identifier
        else if (isIdentifier(nextExpression)) {

            if (head == null) {

                head = tail = new ListItem<ArithmeticExpressionNode>();
                tail.key = new IdentifierExpressionNode(nextExpression);
            } else {
                tail.next = new ListItem<ArithmeticExpressionNode>();
                tail = tail.next;
                tail.key = new IdentifierExpressionNode(nextExpression);


            }

            return buildOperands(expression, operator, head, tail, operandCount + 1);

        } else if (nextExpression.equals(ArithmeticExpressionNode.RIGHT_BRACKET) && head == null) {

            throw new IllegalArgumentException(nextExpression);

        }


        // ein geschachtelter Term ist aufgetaucht - es muss von neuem gestartet werden

        else if (nextExpression.equals(ArithmeticExpressionNode.LEFT_BRACKET)) {

            if (head == null) {

                head = tail = new ListItem<ArithmeticExpressionNode>();
                tail.key = buildRecursivelyHelper(expression, true, true);
            } else {
                tail.next = new ListItem<ArithmeticExpressionNode>();
                tail = tail.next;
                tail.key = buildRecursivelyHelper(expression, true, true);


            }


            buildOperands(expression, operator, head, tail, operandCount + 1);
        }

        // die Operation ist abgeschlossen, der head wird returned oder eine Exception geworfen wenn die anzahl der operanden nicht zusammenpasst
        else if (nextExpression.equals(ArithmeticExpressionNode.RIGHT_BRACKET)) {

            if (operandCount >= Integer.MAX_VALUE) {

                if (operator == Operator.ADD || operator == Operator.MUL) {
                    throw new WrongNumberOfOperandsException(operandCount, 0, Integer.MAX_VALUE);
                }

                if (operator == Operator.SUB || operator == Operator.DIV)
                    throw new WrongNumberOfOperandsException(operandCount, 1, Integer.MAX_VALUE);


            }

            // wenn es den Integer MaxValue überschreiten würde : p!= null & der Maxvalue ist erreicht
            if (operandCount < 1) {

                if (operator == Operator.SUB || operator == Operator.DIV)
                    throw new WrongNumberOfOperandsException(operandCount, 1, Integer.MAX_VALUE);

            }
            if (operandCount != 2) {

                if (operator == Operator.EXPT || operator == Operator.LOG)
                    throw new WrongNumberOfOperandsException(operandCount, 2, 2);
            }
            if (operandCount != 1) {

                if (operator == Operator.EXP || operator == Operator.LN || operator == Operator.SQRT)
                    throw new WrongNumberOfOperandsException(operandCount, 1, 1);
            }


            return new OperationExpressionNode(operator, head);


        } else {
            switch (nextExpression) {
                case "+", "-", "/", "exp", "expt", "ln", "log", "sqrt" -> {
                    throw new ParenthesesMismatchException();
                }

                default -> throw new IllegalIdentifierExceptions(nextExpression);
            }
        }
        return new OperationExpressionNode(operator, head);

    }


    /**
     * überprüft, ob der übergebene String ein Literal darstellt
     *
     * @param expression ist der übergebene String
     * @return true wenn der übergebene String über einstimmt mit : 123 || 12.12 || 12/12
     */

    public static boolean isLiteral(String expression) {


        boolean isDigit = false;


        String regex = "\\d+";


        if (expression.charAt(0) == '-') {
            expression = expression.substring(1);
        }


        String[] splitted = expression.split("\\.");
        // real
        if (splitted.length == 2) {

            if (splitted[0].matches(regex)
                && splitted[1].matches(regex)) {
                isDigit = true;
            }
        }

        String[] splitted2 = expression.split("/");
        //rational

        if (splitted2.length == 2) {


            if (splitted2[0].matches(regex)
                && splitted2[1].matches(regex)) {
                isDigit = true;
            }


        }


        // integer
        if (expression.matches(regex)) {
            isDigit = true;
        }

        return isDigit;
    }


    public static boolean isIdentifier(String expression) {

        boolean isIdentifier = true;


        String regex = "[^A-Z]*[^-]*[^a-z]*";
        String regex1 = "[^A-Z]*[^a-z]*[^-]*";
        String regex2 = "[^a-z]*[^A-Z]*[^-]*";
        String regex3 = "[^a-z]*[^-]*[^A-Z]*";
        String regex4 = "[^-]*[^a-z]*[^A-Z]*";
        String regex5 = "[^-]*[^a-z]*[^A-Z]*";
        String regex6 = "[^A-Z]";
        String regex7 = "[^a-z]";


        if (expression.matches(regex) && expression.matches(regex1) && expression.matches(regex2) && expression.matches(regex3) && expression.matches(regex4)
            && expression.matches(regex5) && expression.matches(regex6) && expression.matches(regex7))
            isIdentifier = false;

        return isIdentifier;


    }


    /**
     * Builds an arithmetic expression tree from a string iteratively.
     *
     * @param expression the string representation of the arithmetic expression to parse
     * @return the root node of the arithmetic expression tree
     * @throws BadOperationException        if the iterator has no more tokens
     * @throws ParenthesesMismatchException if the parentheses are mismatched
     * @throws UndefinedOperatorException   if the operator is not defined
     */
    public static ArithmeticExpressionNode buildIteratively(Iterator<String> expression) {

        if (!expression.hasNext())
            throw new BadOperationException("No expression");

        String nextExpression = expression.next();

        //
        // es ist ein literal ganz am Anfang
        if (isLiteral(nextExpression) && !expression.hasNext()) {

            String[] splitted = nextExpression.split("/");

            //real;
            if (splitted.length == 2) {

                return new LiteralExpressionNode(new MyRational(new Rational(new BigInteger(String.valueOf(splitted[0])), new BigInteger(String.valueOf(splitted[1])))));
            }


            String[] splitted2 = nextExpression.split("\\.");
            // real
            if (splitted2.length == 2) {

                return new LiteralExpressionNode(new MyReal(new BigDecimal(String.valueOf(nextExpression))));
            }


            return new LiteralExpressionNode(new MyInteger(new BigInteger(nextExpression)));

        }


        if (isIdentifier(nextExpression) && !expression.hasNext()) {

            return new IdentifierExpressionNode(nextExpression);

        }

        if (!nextExpression.equals(ArithmeticExpressionNode.LEFT_BRACKET))
            throw new ParenthesesMismatchException();

        // hier kommt ein Operationnode auf jeden Fall und es muss ( vorgekommen sein

        ListItem<ArithmeticExpressionNode> head = null;
        ListItem<ArithmeticExpressionNode> tail = null;

        boolean firstOperation = true;
        boolean newOperation = true;

        while (expression.hasNext()) {

            nextExpression = expression.next();
            Operator operator = Operator.ADD;
            int operandsCount = 0;
            // ( kam vor
            if (newOperation) {

                //()
                if (nextExpression.equals(ArithmeticExpressionNode.RIGHT_BRACKET))
                    throw new UndefinedOperatorException(nextExpression);


                switch (nextExpression) {
                    case "+" -> {
                        operator = Operator.ADD;
                    }
                    case "-" -> {
                        operator = Operator.SUB;
                    }
                    case "*" -> {
                        operator = Operator.MUL;
                    }
                    case "/" -> {
                        operator = Operator.DIV;
                    }
                    case "exp" -> {
                        operator = Operator.EXP;
                    }
                    case "expt" -> {
                        operator = Operator.EXPT;
                    }
                    case "ln" -> {
                        operator = Operator.LN;
                    }
                    case "log" -> {
                        operator = Operator.LOG;
                    }
                    case "sqrt" -> {
                        operator = Operator.SQRT;
                    }
                    default -> throw new UndefinedIdentifierException(nextExpression);
                }


                // (+
                if (!expression.hasNext())
                    throw new IllegalIdentifierExceptions(null);

                nextExpression = expression.next();

                while (isLiteral(nextExpression) || isIdentifier(nextExpression)) {


                    // literal
                    if (isLiteral(nextExpression)) {

                        String[] splitted = nextExpression.split("/");
                        String[] splitted2 = nextExpression.split("\\.");

                        //real;
                        if (splitted.length == 2) {

                            if (head == null) {

                                head = tail = new ListItem<ArithmeticExpressionNode>();
                                tail.key = new LiteralExpressionNode(new MyRational(new Rational(new BigInteger(String.valueOf(splitted[0])), new BigInteger(String.valueOf(splitted[1])))));
                            } else {
                                tail.next = new ListItem<ArithmeticExpressionNode>();
                                tail = tail.next;
                                tail.key = new LiteralExpressionNode(new MyRational(new Rational(new BigInteger(String.valueOf(splitted[0])), new BigInteger(String.valueOf(splitted[1])))));

                            }
                        }

                        // real
                        else if (splitted2.length == 2) {

                            if (head == null){
                                head = tail = new ListItem<ArithmeticExpressionNode>();
                            tail.key = new LiteralExpressionNode(new MyReal(new BigDecimal(String.valueOf(nextExpression))));
                        }
                            else{

                                tail.next = new ListItem<ArithmeticExpressionNode>();
                                tail = tail.next;
                                tail.key = new LiteralExpressionNode(new MyReal(new BigDecimal(String.valueOf(nextExpression))));

                            }




                    }


                    if (isIdentifier(nextExpression) && !expression.hasNext()) {

                        if ( head == null) {

                            head = tail = new ListItem<ArithmeticExpressionNode>();
                            tail.key = new IdentifierExpressionNode(nextExpression);
                        }
                        else {

                            tail.next = new ListItem<ArithmeticExpressionNode>();
                            tail = tail.next;
                            tail.key = new IdentifierExpressionNode(nextExpression);
                        }

                    }


                }

                    nextExpression = expression.next();

            }

                newOperation = false;

        }


    }


            return null;

}

    /**
     * Reconstructs the string representation of the arithmetic expression tree.
     *
     * @param root the root node of the arithmetic expression tree
     * @return the string representation of the arithmetic expression tree
     */
    public static List<String> reconstruct(ArithmeticExpressionNode root) {

        return null;

    }
}
