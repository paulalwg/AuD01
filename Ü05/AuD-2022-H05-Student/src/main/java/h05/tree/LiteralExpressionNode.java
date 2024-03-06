package h05.tree;

import h05.exception.UndefinedIdentifierException;
import h05.math.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a literal operand arithmetic expression node. A literal operand is a {@link
 * MyNumber}.
 *
 * <p>Example:
 * <ul>
 *     <li>Literal node with the number 2.5/li>
 * </ul>
 *
 * <pre>{@code
 *    LiteralExpressionNode node = new LiteralExpressionNode(new MyReal(2.5));
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class LiteralExpressionNode extends OperandExpressionNode {

    /**
     * The literal operand.
     */
    private final MyNumber value;

    /**
     * Constructs and initializes a literal operand arithmetic expression node with the given
     * value.
     *
     * @param value the literal operand
     * @throws NullPointerException if the value is {@code null}
     */
    public LiteralExpressionNode(MyNumber value) {
        Objects.requireNonNull(value, "value null");
        this.value = value;
    }

    /**
     * Returns the literal operand.
     *
     * @return the literal operand
     */
    public MyNumber getValue() {
        return value;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {

        return this.value;
    }

    @Override
    public ArithmeticExpressionNode clone() {


        if ( value instanceof MyInteger){

            return new LiteralExpressionNode(new MyInteger(new BigInteger(String.valueOf(value.toInteger()))));
        }

        if ( value instanceof MyRational){


            Rational rational = new Rational(new BigInteger(String.valueOf(value.toRational().getNumerator())),
                new BigInteger(String.valueOf(value.toRational().getDenominator())));

            return new LiteralExpressionNode(new MyRational(rational));
        }

        if (value instanceof MyReal) {

            return new LiteralExpressionNode(new MyReal(new BigDecimal(String.valueOf(value.toReal()))));


        }


        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
