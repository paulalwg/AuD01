package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a rational number in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyRational extends MyNumber {

    /**
     * The {@link MyNumber} 0 as a {@link MyRational}.
     */
    public static final MyNumber ZERO = new MyRational(Rational.ZERO);

    /**
     * The {@link MyNumber} 1 as a {@link MyRational}.
     */
    public static final MyNumber ONE = new MyRational(Rational.ONE);

    /**
     * The value of this rational number.
     */
    private final Rational value;

    /**
     * Constructs and initializes a rational number with the specified value.
     *
     * @param value the value of the rational number
     * @throws NullPointerException if the value is null
     */
    public MyRational(Rational value) {
        this.value = Objects.requireNonNull(value, "value null");
    }

    @Override
    public BigInteger toInteger() {
        return value.getNumerator().divide(value.getDenominator());
    }

    @Override
    public Rational toRational() {


        return new Rational(value.getNumerator(), value.getDenominator());
    }

    @Override
    public BigDecimal toReal() {

        BigDecimal real = new BigDecimal(value.getNumerator()).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE).
            divide(new BigDecimal(value.getDenominator()).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE), MyReal.ROUNDING_MODE);


        return real.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);


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
        if (!(o instanceof MyRational number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyRational(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        if (other instanceof MyInteger) {
            return checkRationalToInt(value.plus(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().add(other.toReal()));
        }
        return checkRationalToInt(value.plus(other.toRational()));
    }

    @Override
    public MyNumber minus() {


        return checkRationalToInt(this.value.negate());


    }

    @Override
    public MyNumber minus(MyNumber other) {


        if (other instanceof MyRational) {

            return checkRationalToInt(new Rational(value.getNumerator().multiply(((MyRational) other).value.getDenominator()).subtract(value.getDenominator().multiply(((MyRational) other).value.getNumerator())),
                value.getDenominator().multiply(((MyRational) other).value.getDenominator())));

        } else if (other instanceof MyInteger) {

            return checkRationalToInt(new Rational(value.getNumerator().multiply(BigInteger.ONE).subtract(value.getDenominator().multiply(other.toInteger())),
                value.getDenominator().multiply(BigInteger.ONE)));
        } else {


            return checkRealToInt(this.toReal().subtract(other.toReal()));
        }
    }

    @Override
    public MyNumber times(MyNumber other) {
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().multiply(other.toReal()));
        }
        return checkRationalToInt(value.times(other.toRational()));
    }

    @Override
    public MyNumber divide() {

        if (this.value.getNumerator().intValue() == 0)
            throw new WrongOperandException(this, Comparison.DIFFERENT_FROM, new MyInteger(BigInteger.ZERO));


        if (value.getNumerator().signum() < 0) {


            return checkRationalToInt(new Rational(value.getDenominator().negate(), value.getNumerator().negate()));
        }

        return checkRationalToInt(new Rational(value.getDenominator(), value.getNumerator()));


    }

    @Override
    public MyNumber divide(MyNumber other) {

        if (other.toReal().doubleValue() == 0)
            throw new WrongOperandException(other, Comparison.DIFFERENT_FROM, new MyInteger(BigInteger.ZERO));

        if (other instanceof MyRational) {

            return checkRationalToInt(new Rational(this.value.getNumerator().multiply(other.toRational().getDenominator()),
                this.value.getDenominator().multiply(other.toRational().getNumerator())));
        }

        if (other instanceof MyInteger) {


            return checkRationalToInt(new Rational(
                this.value.getNumerator(), this.value.getDenominator().multiply(other.toRational().getNumerator())));

        } else {

            return checkRealToInt(this.toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
