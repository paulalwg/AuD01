package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents an integer in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyInteger extends MyNumber {

    /**
     * The constant {@link MyNumber} 0 as a {@link MyInteger}.
     */
    public static final MyNumber ZERO = new MyInteger(BigInteger.ZERO);

    /**
     * The constant {@link MyNumber} 1 as a {@link MyInteger}.
     */
    public static final MyNumber ONE = new MyInteger(BigInteger.ONE);

    /**
     * The value of the integer.
     */
    private final BigInteger value;

    /**
     * Constructs and initializes an integer with the specified value.
     *
     * @param value the value of the real number
     * @throws NullPointerException if the value is null
     */
    public MyInteger(BigInteger value) {
        this.value = Objects.requireNonNull(value, "value null");
    }

    @Override
    public BigInteger toInteger() {
        return value;
    }

    @Override
    public Rational toRational() {


        BigInteger denominator = BigInteger.ONE;

        return new Rational(value, denominator);
    }

    @Override
    public BigDecimal toReal() {

        BigDecimal result = new BigDecimal(String.valueOf(value));

        return result.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
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
        if (!(o instanceof MyInteger number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyInteger(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.add((other.toInteger())));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().add(other.toReal()));
        }
        return checkRationalToInt(other.toRational().plus(value));
    }

    @Override
    public MyNumber minus(MyNumber other) {

        if (other instanceof MyRational) {

            return checkRationalToInt(new Rational(new BigInteger(String.valueOf(this.value.multiply(other.toRational().getDenominator()))).subtract(other.toRational().getNumerator()), other.toRational().getDenominator()));

        } else {

            return checkRealToInt(this.toReal().subtract(other.toReal()));
        }
    }


    @Override
    public MyNumber times(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.multiply(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().multiply(other.toReal()));
        }
        return checkRationalToInt(other.toRational().times(value));
    }

    @Override
    public MyNumber divide() {
        if (this.value.intValue() == 0)
            throw new WrongOperandException(this, Comparison.DIFFERENT_FROM, new MyInteger(new BigInteger("0")));

        return checkRationalToInt(new Rational(BigInteger.ONE, this.value));
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other.toReal().doubleValue() == 0) {
            throw new WrongOperandException(other, Comparison.DIFFERENT_FROM, new MyInteger(new BigInteger("0")));
        }

        if (other instanceof MyInteger) {


            return checkRationalToInt(new Rational(this.toRational().getNumerator().divide(other.toRational().getNumerator()), this.toRational().getDenominator()));
        } else if (other instanceof MyRational) {

            boolean isNegative = other.toRational().getNumerator().signum() < 0;


            MyNumber myNumber = checkRationalToInt(new Rational(this.toRational().getNumerator().multiply(other.toRational().getDenominator()), other.toRational().getNumerator()));

            if (isNegative)
                return myNumber.negate();

            return myNumber;

        } else {

            return checkRealToInt(this.toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
