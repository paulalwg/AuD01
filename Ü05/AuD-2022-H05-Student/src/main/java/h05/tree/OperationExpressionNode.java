package h05.tree;

import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyInteger;
import h05.math.MyNumber;
import jdk.dynalink.Operation;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents an operation arithmetic expression node. An operation expression node
 * contains and operator followed by
 * <it>n</it> operands depending on its arity.
 *
 * <p>Example:
 * <ul>
 *     <li>Operator node with the operation "+" and the operands 2, 3, 4/li>
 *     <li>Racket notation: (+ 2 3 4)</li>
 * </ul>
 *
 * <pre>{@code
 *    ListItem<ArithmeticExpressionNode> operands = new ListItem<>();
 *    operands.key = new LiteralExpressionNode(new MyInteger(2));
 *    operands.next = new ListItem<>();
 *    operands.next.key = new LiteralExpressionNode(new MyInteger(3));
 *    operands.next.next = new ListItem<>();
 *    operands.next.next.key = new LiteralExpressionNode(new MyInteger(4));
 *    OperationExpressionNode node = new OperationExpressionNode(Operator.ADD, operands);
 * }</pre>
 *
 * @author Nhan Huynh
 * @see Operator
 * @see ListItem
 */
public class OperationExpressionNode implements ArithmeticExpressionNode {

    /**
     * The operator of this node.
     */
    private final Operator operator;

    /**
     * The operands of this node.
     */
    private final @Nullable ListItem<ArithmeticExpressionNode> operands;

    /**
     * Contracts and initializes an operation expression node with the given operator and operands.
     *
     * @param operator the operator of this node
     * @param operands the operands of this node
     * @throws NullPointerException if the operator is {@code null}
     */
    public OperationExpressionNode(Operator operator, @Nullable ListItem<ArithmeticExpressionNode> operands) {
        Objects.requireNonNull(operator, "operator null");

        int operandCount = 0;

        ListItem<ArithmeticExpressionNode> p = operands;

        // zählen der Operanden
        while (p != null) {
            operandCount++;
            p = p.next;

            // wenn der Maxwert überschritten werden würde dann wird entweder eine Exception geworfen
            // oder die schleife unterbrochen
            if (operandCount == Integer.MAX_VALUE && p != null) {

                if (operator == Operator.ADD || operator == Operator.MUL) {
                    throw new WrongNumberOfOperandsException(operandCount, 0, Integer.MAX_VALUE);
                }

                break;
            }
        }

        // wenn es den Integer MaxValue überschreiten würde : p!= null & der Maxvalue ist erreicht
        if (operandCount < 1 || (operandCount == Integer.MAX_VALUE && p != null)) {

            if (operator == Operator.SUB || operator == Operator.DIV)
                throw new WrongNumberOfOperandsException(operandCount, 1, Integer.MAX_VALUE);

        }

        if (operandCount != 1) {

            if (operator == Operator.EXP || operator == Operator.LN || operator == Operator.SQRT)
                throw new WrongNumberOfOperandsException(operandCount, 1, 1);
        }


        if (operandCount != 2) {

            if (operator == Operator.EXPT || operator == Operator.LOG)
                throw new WrongNumberOfOperandsException(operandCount, 2, 2);
        }

        this.operands = operands;

        this.operator = operator;


    }

    /**
     * Returns the operator of this node.
     *
     * @return the operator of this node
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Returns the operands of this node.
     *
     * @return the operands of this node
     */
    public ListItem<ArithmeticExpressionNode> getOperands() {
        return operands;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {

        ListItem<ArithmeticExpressionNode> p = operands;

        int count = 0;

        while (p != null) {
            count++;
            p = p.next;
        }

        // wenn 0 operanden vorhanden sind
        if (count == 0)
            return new MyInteger(BigInteger.ZERO);

        if (count == 1) {

            switch (operator) {


                case ADD:
                    return operands.key.evaluate(identifiers).plus();


                case SUB:
                    return operands.key.evaluate(identifiers).minus();


                case MUL:
                    return operands.key.evaluate(identifiers).times();


                case DIV:
                    return operands.key.evaluate(identifiers).divide();


                case EXP:
                    return operands.key.evaluate(identifiers).exp();


                case LN:
                    return operands.key.evaluate(identifiers).ln();


                case SQRT:
                    return operands.key.evaluate(identifiers).sqrt();

            }

        }


        if (count == 2) {

            assert operands.next != null;

            switch (operator) {


                case ADD:
                    return operands.key.evaluate(identifiers).plus(operands.next.key.evaluate(identifiers));


                case SUB:
                    return operands.key.evaluate(identifiers).minus(operands.next.key.evaluate(identifiers));


                case MUL:
                    return operands.key.evaluate(identifiers).times(operands.next.key.evaluate(identifiers));


                case DIV:
                    return operands.key.evaluate(identifiers).divide(operands.next.key.evaluate(identifiers));


                case EXPT:
                    return operands.key.evaluate(identifiers).expt(operands.next.key.evaluate(identifiers));


                case LOG:
                    return operands.key.evaluate(identifiers).log(operands.next.key.evaluate(identifiers));


            }
        }


        return switch (operator) {
            case ADD -> operands.key.evaluate(identifiers).plus(evaluate(identifiers));
            case SUB -> operands.key.evaluate(identifiers).minus(evaluate(identifiers));
            case MUL -> operands.key.evaluate(identifiers).times(evaluate(identifiers));
            case DIV -> operands.key.evaluate(identifiers).divide(evaluate(identifiers));
            default -> null;
        };

    }


    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public ArithmeticExpressionNode clone() {

        ListItem<ArithmeticExpressionNode> p = operands;

        ListItem<ArithmeticExpressionNode> clonedOperandsHead = null;
        ListItem<ArithmeticExpressionNode> tail = null;

        // erzeugen der geklonten Liste
        while (p != null) {

            if (clonedOperandsHead == null) {
                clonedOperandsHead = tail = new ListItem<ArithmeticExpressionNode>();
                tail.key = p.key.clone();
                p = p.next;
                continue;
            }

            tail.next = new ListItem<ArithmeticExpressionNode>();
            tail = tail.next;
            tail.key = p.key.clone();

            p = p.next;
        }


        Operator operator = Operator.ADD;

        if (this.operator == Operator.SUB)
            operator = Operator.SUB;

        if (this.operator == Operator.MUL)
            operator = Operator.MUL;

        if (this.operator == Operator.DIV)
            operator = Operator.DIV;

        if (this.operator == Operator.EXP)
            operator = Operator.EXP;

        if (this.operator == Operator.EXPT)
            operator = Operator.EXPT;

        if (this.operator == Operator.LN)
            operator = Operator.LN;

        if (this.operator == Operator.LOG)
            operator = Operator.LOG;

        if (this.operator == Operator.SQRT)
            operator = Operator.SQRT;


        return new OperationExpressionNode(operator, clonedOperandsHead);


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LEFT_BRACKET);
        sb.append(operator);
        for (ListItem<ArithmeticExpressionNode> node = operands; node != null; node = node.next) {
            sb.append(" ").append(node.key);
        }
        sb.append(RIGHT_BRACKET);
        return sb.toString();
    }
}
