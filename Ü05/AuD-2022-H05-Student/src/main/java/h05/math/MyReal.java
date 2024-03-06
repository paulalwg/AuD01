package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a real number in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyReal extends MyNumber {

    /**
     * The scale of the real number for inexact numbers.
     */
    public static final int SCALE = 15;

    /**
     * The rounding mode of the real number for inexact numbers.
     */
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * The constant {@link MyNumber} 0 as a {@link MyReal}.
     */
    public static final MyNumber ZERO = new MyReal(BigDecimal.ZERO);

    /**
     * The constant {@link MyNumber} 1 as a {@link MyReal}.
     */
    public static final MyNumber ONE = new MyReal(BigDecimal.ONE);

    /**
     * The value of this real number.
     */
    private final BigDecimal value;

    /**
     * Constructs and initializes a real number with the specified value.
     *
     * @param value the value of the real number
     * @throws NullPointerException if the value is null
     */
    public MyReal(BigDecimal value) {
        Objects.requireNonNull(value, "value null");
        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }


    @Override
    public BigInteger toInteger() {
        return value.toBigInteger();
    }

    @Override
    public Rational toRational() {

        boolean isNegative = value.signum() < 0;


        if (isNegative) {

            // value*(-1) - (value*(-1) - (int) value(*(-1))
            BigDecimal numeratorAsDecimal = value.negate().subtract(value.negate().subtract(new BigDecimal(value.negate().toBigInteger()).setScale(SCALE, ROUNDING_MODE)));


            BigInteger numerator = numeratorAsDecimal.toBigInteger().negate();


            return new Rational(numerator, BigInteger.ONE);
        } else {


            BigInteger denominator = new BigInteger("10").pow(SCALE);

            BigDecimal numeratorAsDecimal = new BigDecimal(String.valueOf(value.doubleValue())).setScale(SCALE, ROUNDING_MODE).multiply(new BigDecimal("10").pow(SCALE));


            BigInteger numerator = numeratorAsDecimal.toBigInteger();

            return new Rational(numerator, denominator);
        }


    }

    @Override
    public BigDecimal toReal() {

        return value.setScale(SCALE, ROUNDING_MODE);
    }

    @Override
    public boolean isZero() {
        return this.equals(ZERO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyReal number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyReal(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        return checkRealToInt(value.add(other.toReal()));
    }

    @Override
    public MyNumber minus() {

        return new MyReal(new BigDecimal("0").subtract(value));
    }


    @Override
    public MyNumber times(MyNumber other) {
        return checkRealToInt(value.multiply(other.toReal()));
    }

    @Override
    public MyNumber divide() {

        if (this.value.doubleValue() == 0)
            throw new WrongOperandException(this, Comparison.DIFFERENT_FROM, new MyInteger(BigInteger.ZERO));


        BigDecimal bigDecimal = BigDecimal.ONE.setScale(SCALE, ROUNDING_MODE);

        return new MyReal(bigDecimal.divide(this.value,ROUNDING_MODE));



    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other.toReal().doubleValue() == 0) {
            throw new WrongOperandException(other, Comparison.DIFFERENT_FROM, new MyInteger(BigInteger.ZERO));
        }

        return checkRealToInt(this.toReal().divide(other.toReal(), ROUNDING_MODE));
    }


    @Override
    public String toString() {
        return value.stripTrailingZeros().toString();
    }
}
