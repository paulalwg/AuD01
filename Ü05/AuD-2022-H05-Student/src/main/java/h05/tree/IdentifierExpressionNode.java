package h05.tree;

import h05.exception.IllegalIdentifierExceptions;
import h05.exception.UndefinedIdentifierException;
import h05.math.MyNumber;

import java.util.Map;

/**
 * This class represents an identifier operand arithmetic expression node. An identifier operand is
 * a variable name.
 *
 * <p>Example:
 * <ul>
 *     <li>Identifier node with the identifier a</li>
 *     <li>Racket notation: (define a 2) - constant identifier a with the value 2</li>
 * </ul>
 *
 * <pre>{@code
 *    IdentifierExpressionNode node = new IdentifierExpressionNode("a");
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class IdentifierExpressionNode extends OperandExpressionNode {
    /**
     * The identifier name.
     */
    private final String value;

    /**
     * Constructs and initializes an identifier expression node with the given value.
     *
     * @param value the identifier name
     * @throws IllegalArgumentException    if the identifier name is not valid
     * @throws IllegalIdentifierExceptions if the identifier name is not valid
     * @throws NullPointerException        if the identifier name is {@code null}
     */
    public IdentifierExpressionNode(String value) {

        if (value == null)
            throw new NullPointerException();

        if (value.equals(""))
            throw new IllegalArgumentException("empty string");


        boolean containslowercase = false;
        boolean containsCapitalLetter = false;
        boolean containsHyphen = false;

        for (int i = 0; i < value.length(); i++) {

            if (value.charAt(i) >= 97 && value.charAt(i) <= 122) {
                containslowercase = true;
                continue;
            }

            if (value.charAt(i) >= 65 && value.charAt(i) <= 90) {
                containsCapitalLetter = true;
                continue;
            }

            if (value.charAt(i) == 45) {
                containsHyphen = true;
                continue;
            }

            // wenn ein nicht erlaubtes char da ist wird eine Exception geworfen
            throw new IllegalArgumentException(value);

        }


        // wenn Groß oder Kleinbuchstaben vorhanden sind und optional hyphen wird der Wert zugewiesen
        if (containslowercase || containsCapitalLetter)
            this.value = value;


            // - -- --- falls nur Hyphen da sind wird auch eine Exception geworfen
        else {


            throw new IllegalArgumentException(value);

        }


    }

    /**
     * Returns the identifier name.
     *
     * @return the identifier name
     */
    public String getValue() {
        return value;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {


        if (value.equals(Identifier.E.toString()) || value.equals(Identifier.PI.toString()) )
            throw new IllegalIdentifierExceptions(value);

        if (!identifiers.containsKey(value))
            throw new UndefinedIdentifierException(value);

         return identifiers.get(value);

    }

    @Override
    public ArithmeticExpressionNode clone() {

        return new IdentifierExpressionNode(String.valueOf(value));

    }

    @Override
    public String toString() {
        return value;
    }
}
