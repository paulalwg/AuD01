package h05;

import h05.math.MyInteger;
import h05.math.MyRational;
import h05.math.MyReal;
import h05.math.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

/*//TEst H1.1
        BigInteger numerator = new BigInteger("8");
        BigInteger denominator = new BigInteger("-2");

        Rational rational = new Rational(numerator, denominator);

        System.out.println("numerator : " + rational.getNumerator());
        System.out.println("denominator : " + rational.getDenominator());

 */

       /*
        // Test H1.2

         // MyRational test = new MyRational(new Rational(new BigInteger("-100") , new BigInteger("20")));
         //MyInteger test = new MyInteger(new BigInteger("-18");
        MyReal test = new MyReal(new BigDecimal("-15.46464"));

System.out.println ( "toReal : " + test.toReal());
System.out.println ( "toRational : " + test.toRational());

        */



        MyRational testRat = new MyRational(new Rational(new BigInteger("1") , new BigInteger("5")));
        MyInteger testInt = new MyInteger(new BigInteger("0"));
        MyReal testReal = new MyReal(new BigDecimal("-0.25"));


        MyRational testRat2 = new MyRational(new Rational(new BigInteger("15") , new BigInteger("5")));
        MyInteger testInt2 = new MyInteger(new BigInteger("5"));
        MyReal testReal2 = new MyReal(new BigDecimal("2.5"));





        try {
            System.out.println(testReal.log(testInt));

            System.out.println(testReal.log(testInt));
        }

        catch ( Exception e) {

            System.out.println(e.getMessage());
        }


        System.out.println("a :  " + (int) 'a');
        System.out.println("z :  " + (int) 'z');

        System.out.println("A :  " + (int) 'A');
        System.out.println("Z :  " + (int) 'Z');

        System.out.println("- :  " + (int) '-');



        String expression = "E";

        boolean isIdentifier = true;



        String regex = "[^A-Z]*[^-]*[^a-z]*";
        String regex1 = "[^A-Z]*[^a-z]*[^-]*";
        String regex2 = "[^a-z]*[^A-Z]*[^-]*";
        String regex3 = "[^a-z]*[^-]*[^A-Z]*";
        String regex4 = "[^-]*[^a-z]*[^A-Z]*";
        String regex5 = "[^-]*[^a-z]*[^A-Z]*";
        String regex6 = "[^A-Z]";
        String regex7 = "[^a-z]";






           if ( expression.matches(regex) && expression.matches(regex1) && expression.matches(regex2)&& expression.matches(regex3)  && expression.matches(regex4)
               && expression.matches(regex5)  && expression.matches(regex6)  && expression.matches(regex7))
                isIdentifier = false;





        System.out.println("isIdentifier : " + isIdentifier );






















        System.out.println("Hello World!");
    }
}
