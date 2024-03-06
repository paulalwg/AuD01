package h05.tree;

import h05.math.MyNumber;

import java.util.List;
import java.util.Map;

/**
 * Evaluates an arithmetic expression by replacing the variables (identifiers) of the expression
 * with their values.
 *
 * @author Nhan Huynh
 */
public class ArithmeticExpressionEvaluator {

    /**
     * The arithmetic expression tree to evaluate.
     */
    private ArithmeticExpressionNode root;

    /**
     * The map of variables and their values.
     */
    private final Map<String, MyNumber> identifiers;

    /**
     * Constructs and initializes an arithmetic expression evaluator.
     *
     * @param root        the root of the arithmetic expression tree to evaluate
     * @param identifiers the map of variables and their values
     */
    public ArithmeticExpressionEvaluator(
        ArithmeticExpressionNode root,
        Map<String, MyNumber> identifiers) {
        this.root = root.clone();
        this.identifiers = identifiers;
    }

    /**
     * Returns the root of the arithmetic expression tree to evaluate.
     *
     * @return the root of the arithmetic expression tree to evaluate
     */
    public ArithmeticExpressionNode getRoot() {
        return root;
    }

    /**
     * Returns the map of variables and their values.
     *
     * @return the map of variables and their values
     */
    public Map<String, MyNumber> getIdentifiers() {
        return identifiers;
    }

    /**
     * Evaluates the arithmetic expression tree by replacing the variables (identifiers) of the
     * expression with their values and evaluates the most inner expressions.
     *
     * @return the list of tokens representing  the evaluation
     */
    public List<String> nextStep() {
        throw new RuntimeException("H5 - not implemented"); // TODO: H5 - remove if implemented
    }
}
