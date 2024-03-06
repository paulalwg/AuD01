package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * The abstract class Number represents the numbers of the programming language Racket in a very
 * simplified way.
 *
 * @author Nhan Huynh, Jonas Renk
 * @see <a href="https://docs.racket-lang.org/reference/number-types.html">
 * https://docs.racket-lang.org/reference/number-types.html</a>
 */
public abstract class MyNumber {

    /**
     * Returns the representation of this number as an integer.
     *
     * @return the representation of this number as an integer.
     */
    public abstract BigInteger toInteger();

    /**
     * Returns the representation of this number as a rational number.
     *
     * @return the representation of this number as a rational number.
     */
    public abstract Rational toRational();

    /**
     * Returns the representation of this number as a real number.
     *
     * @return the representation of this number as a real number.
     */
    public abstract BigDecimal toReal();

    /**
     * Returns {@code true} if this number is zero.
     *
     * @return {@code true} if this number is zero
     */
    public abstract boolean isZero();

    /**
     * Returns the hash code for this {@code MyNumber}. The hash code is computed by the value
     * representation of this number.
     *
     * @return hash code for this {@code MyNumber}.
     * @see #equals(Object)
     */
    @Override
    public abstract int hashCode();

    /**
     * Compares this {@code MyNumber} with the specified {@code Object} for equality. Two {@code
     * MyNumber} objects equal only if they are equal their class and their value representation.
     * Therefore, 0.5 is not equal to 1/2 when compared by this.
     *
     * @param obj {@code Object} to which this {@code MyNumber} is to be compared.
     * @return {@code true} if and only if the specified {@code Object} is a {@code MyNumber} whose
     * value representation and class are equal to this {@code MyNumber}'s.
     * @see #hashCode
     */
    @Override
    public abstract boolean equals(@Nullable Object obj);

    /**
     * Returns the string representation of this {@code MyNumber}.
     *
     * @return the string representation of this {@code MyNumber}.
     */
    @Override
    public abstract String toString();

    /**
     * Returns a number whose value is {@code (-this)}.
     *
     * @return {@code -this}
     */
    public abstract MyNumber negate();

    /**
     * Returns the sum of this number and the neutral element 0 {@code 0 + this}.
     *
     * @return the sum of this number and the neutral element 0
     */
    public MyNumber plus() {
        return this;
    }

    /**
     * Returns the sum of this number and the given number ({@code this + other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to add
     * @return the sum of this number and the given number
     */
    public abstract MyNumber plus(MyNumber other);

    /**
     * Returns the difference of this number and the neutral element 0 {@code 0 - this}.
     *
     * @return the difference of this number and the neutral element 0
     */
    public MyNumber minus() {

        return checkRealToInt(new BigDecimal("0").subtract(this.toReal()));


    }

    ;

    /**
     * Returns the difference of this number and the given number ({@code this - other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to subtract
     * @return the difference of this number and the given number
     */
    public MyNumber minus(MyNumber other) {

        return checkRealToInt(this.toReal().subtract(other.toReal()));
    }

    /**
     * Returns the product of this number and the neutral element 1 {@code 1 * this}.
     *
     * @return the product of this number and the neutral element 1
     */
    public MyNumber times() {
        return this;
    }

    /**
     * Returns the product of this number and the given number ({@code this * other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to multiply
     * @return the product of this number and the given number
     */
    public abstract MyNumber times(MyNumber other);

    /**
     * Returns the quotient of this number and the neutral element 1 ({@code 1 / this}).
     *
     * <ol>
     *     <li>If the number is an integer, the result will be rational</li>
     *     <li>If the number is an real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * @return the quotient of this number and the neutral element 1
     * @throws WrongOperandException if the number is 0
     */
    public abstract MyNumber divide();

    /**
     * Returns the quotient of this number and the given number ({@code this / other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an rational</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to divide
     * @return the quotient of this number and the given number
     * @throws WrongOperandException if the given number is 0
     */
    public abstract MyNumber divide(MyNumber other);

    /**
     * Returns the square root of this number. The result will always be real or an integer.
     *
     * @return the square root of this number
     */
    public MyNumber sqrt() {

        return checkRealToInt(this.toReal().sqrt(MathContext.DECIMAL128));


    }

    /**
     * Returns {@code this} number raised to the power of {@code n} (x^n). The result will always be
     * real or an integer.
     *
     * @param n the exponent
     * @return {@code this} number raised to the power of {@code n}
     */
    public MyNumber expt(MyNumber n) {

        if (this.toReal().compareTo(BigDecimal.ZERO) <= 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);

        if (n.toReal().compareTo(BigDecimal.ZERO) <= 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);

        if (this instanceof MyInteger && n instanceof MyInteger)
            return new MyInteger(this.toInteger().pow(n.toInteger().intValue()));

        else {


            // die Zahlen hinterm komma
            BigDecimal nR = n.toReal().subtract(new BigDecimal(n.toInteger())).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            // die ganzzahl
            BigDecimal nN = n.toReal().subtract(nR).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            return checkRealToInt(this.toReal().pow(nN.intValue(), MathContext.DECIMAL128).multiply(new BigDecimal(String.valueOf(Math.pow(this.toReal().doubleValue(), nR.doubleValue()))).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE)));


        }


    }

    /**
     * Returns Euler’s number raised to the power of {@code this} number (exp(x)). The result will
     * always be real or an integer.
     *
     * @return Euler’s number raised to the power of {@code this}
     * @throws WrongOperandException if this number is not positive or the large
     */
    public MyNumber exp() {

        if (this.toReal().compareTo(BigDecimal.ZERO) <= 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);

        if (this.toReal().compareTo(new BigDecimal("2000000000")) > 0)
            throw new WrongOperandException(this, Comparison.LESS_THAN, new MyReal(new BigDecimal("2000000000")));

        BigDecimal e = new BigDecimal(String.valueOf(Math.E)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);


        // die Zahlen hinterm komma
        BigDecimal nR = this.toReal().subtract(new BigDecimal(this.toInteger())).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

        // die ganzzahl
        BigDecimal nN = this.toReal().subtract(nR).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

        return checkRealToInt(e.pow(nN.intValue(), MathContext.DECIMAL128).multiply(new BigDecimal(String.valueOf(Math.pow(Math.E, nR.doubleValue()))).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE)));

    }





    /**
     * Returns the natural logarithm of this number (ln(x)). The result will always be real or an
     * integer.
     *
     * @return the natural logarithm of this number
     * @throws WrongOperandException if this number is not positive
     */
    public MyNumber ln() {

        // lnx = log10x / log10e

        if (this.toReal().compareTo(BigDecimal.ZERO) <= 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);


        BigDecimal resultDividend = new BigDecimal(String.valueOf(Math.log10(this.toReal().doubleValue()))).setScale(MyReal.SCALE,MyReal.ROUNDING_MODE);
        BigDecimal resultDivisor = new BigDecimal(String.valueOf(Math.log10(Math.E))).setScale(MyReal.SCALE,MyReal.ROUNDING_MODE);

        if (this.toReal().compareTo(new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE)) > 0) {

            BigDecimal p = this.toReal().setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
            BigDecimal divisor = new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE);

            int logToAdd = 0;


            // umwandeln bis die Zahl kleiner 10 ist : durch 10 teilen und den logtoAdd erhöhen
            while (p.compareTo(divisor) > 0) {

                p = p.divide(divisor);
                logToAdd++;
            }


            resultDividend = new BigDecimal(String.valueOf(Math.log10(p.doubleValue()) + logToAdd)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

        }


        if (this.toReal().compareTo(BigDecimal.ONE) < 0) {

            BigDecimal p = this.toReal().setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            BigDecimal ten = new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE);

            int logToAdd = 0;

            while (p.compareTo(BigDecimal.ONE) < 0) {
                p = p.multiply(ten);
                logToAdd++;
            }


            resultDividend = new BigDecimal(String.valueOf(Math.log10(p.doubleValue()) - logToAdd)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

        }


        return checkRealToInt(new BigDecimal(String.valueOf(resultDividend.divide(resultDivisor, MyReal.ROUNDING_MODE))).setScale(MyReal.SCALE,MyReal.ROUNDING_MODE));

    }


    /**
     * Returns the logarithm of this number with base {@code base} (log_x(y)). The result will
     * always be real or an integer.
     *
     * @param base the base of the logarithm
     * @return the logarithm of this number with base {@code base}
     * @throws WrongOperandException if this number is not positive or the base is not positive
     */
    public MyNumber log(MyNumber base) {


        if (this.toReal().compareTo(BigDecimal.ZERO) <= 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);

        if (base.toReal().compareTo(BigDecimal.ZERO) <= 0)
            throw new WrongOperandException(base, Comparison.GREATER_THAN, MyInteger.ZERO);


        // logbase(Mynumber) = log10(Mynumber)/log10(base)

        // Exceptionwurf
        if (this.toReal().intValue() < 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);

        if (base.toReal().intValue() < 0)
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);


        if (this.toReal().compareTo(new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE)) > 0 || base.toReal().compareTo(new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE)) > 0) {

            BigDecimal p = this.toReal().setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
            BigDecimal divisor = new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE);

            int logToAdd = 0;


            // umwandeln bis die Zahl kleiner 10 ist : durch 10 teilen und den logtoAdd erhöhen
            while (p.compareTo(divisor) > 0) {

                p = p.divide(divisor);
                logToAdd++;
            }


            BigDecimal resultDividend = new BigDecimal(String.valueOf(Math.log10(p.doubleValue()) + logToAdd)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            BigDecimal b = base.toReal().setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            int logToAdd2 = 0;

            // umwandeln bis die Zahl kleiner 10 ist : durch 10 teilen und den logToAdd erhöhen
            while (b.compareTo(divisor) > 0) {

                b = b.divide(divisor);
                logToAdd2++;
            }

            BigDecimal resultDivisor = new BigDecimal(String.valueOf(Math.log10(b.doubleValue()) + logToAdd2)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);


            return checkRealToInt(new BigDecimal(String.valueOf(resultDividend.divide(resultDivisor, MyReal.ROUNDING_MODE))).setScale(MyReal.SCALE,MyReal.ROUNDING_MODE));

        }

        if (this.toReal().compareTo(BigDecimal.ONE) < 0 || base.toReal().compareTo(BigDecimal.ONE) < 0) {

            // logbase(Mynumber) = log10(Mynumber)/log10(base)


            BigDecimal p = this.toReal().setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            BigDecimal ten = new BigDecimal("10").setScale(MyReal.SCALE,MyReal.ROUNDING_MODE);

            int logToAdd = 0;

            while (p.compareTo(BigDecimal.ONE) < 0) {
                p = p.multiply(ten);
                logToAdd++;
            }


            BigDecimal resultDividend = new BigDecimal(String.valueOf(Math.log10(p.doubleValue()) - logToAdd)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);


            BigDecimal b = base.toReal().setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

            int logToAdd2 = 0;

            while (b.compareTo(BigDecimal.ONE) < 0) {
                b = b.multiply(ten);
                logToAdd2++;
            }

            BigDecimal resultDivisor = new BigDecimal(String.valueOf(Math.log10(b.doubleValue()) - logToAdd2)).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
            ;


            return checkRealToInt(new BigDecimal(String.valueOf(resultDividend.divide(resultDivisor, MyReal.ROUNDING_MODE))).setScale(MyReal.SCALE,MyReal.ROUNDING_MODE));

        }

        // logbase(Mynumber) = log10(Mynumber)/log10(base)

        return checkRealToInt(new BigDecimal(String.valueOf(Math.log10(this.toReal().doubleValue()) / Math.log(base.toReal().doubleValue()))).setScale(MyReal.SCALE,MyReal.ROUNDING_MODE));


    }

    /**
     * Checks if the given real number can be represented as an integer.
     *
     * @param real the real number to check
     * @return an integer if the real number can be represented as an integer, otherwise the real
     * number
     */
    protected MyNumber checkRealToInt(BigDecimal real) {
        BigDecimal stripped = real
            .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE)
            .stripTrailingZeros();

        if (stripped.scale() <= 0) {
            return new MyInteger(stripped.toBigIntegerExact());
        }

        return new MyReal(real);
    }

    /**
     * Checks if the given rational number can be represented as an integer.
     *
     * @param rational the real number to check
     * @return an integer if the rational number can be represented as an integer, otherwise the
     * rational number
     */
    protected MyNumber checkRationalToInt(Rational rational) {
        if (rational.getDenominator().equals(BigInteger.ONE)) {
            return new MyInteger(rational.getNumerator());
        }
        return new MyRational(rational);
    }
}
