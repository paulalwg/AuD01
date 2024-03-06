package h04;

import h04.collection.MyCollections;
import h04.function.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublicTests {

    private static final Comparator<Integer> CMP = Comparator.naturalOrder();

    @Nested
    class DoubleToIntFunctionTest {

        @Test
        void testLinearDoubleToIntFunction() {
            var function = new LinearDoubleToIntFunction(3, 10);
            assertEquals(10, function.apply(0.0));
            assertEquals(12, function.apply(0.5));
            assertEquals(13, function.apply(1.0));
        }

        @Test
        void testArrayDoubleToIntFunction() {
            var function = new ArrayDoubleToIntFunction(new int[]{ 10, 4, 5, 1, 8 });
            assertEquals(7, function.apply(0.125));
            assertEquals(1, function.apply(0.75));
        }
    }

    @Nested
    class ListToIntFunctionTest {

        private static final List<Integer> SEQ = List.of(
            1, 2, 3, 4, 5,
            3, 4, 5, 5,
            4,
            3,
            2, 2, 2, 5, 6, 6, 7, 6, 8, 9,
            5,
            1, 2, 3, 4, 5
        );

        private static final DoubleToIntFunction FUNCTION = x -> (int) (x * 100);

        @Test
        void testFunctionOnRationOfRuns() {
            FunctionOnRatioOfRuns<Integer> function = new FunctionOnRatioOfRuns<>(CMP, FUNCTION);
            assertEquals(29, function.apply(SEQ));
        }

        @Test
        void testFunctionOnRationOfInversions() {
            FunctionOnRatioOfInversions<Integer> function = new FunctionOnRatioOfInversions<>(CMP, FUNCTION);
            assertEquals(32, function.apply(SEQ));
        }
    }

    @Nested
    class MyCollectionsTest {

        @Test
        void testSort() {
            List<Integer> toSort = new ArrayList<>(List.of(
                2, 3,
                1, 4, 5,
                0, 6, 9,
                7, 8));
            List<Integer> expected = toSort.stream().sorted(CMP).toList();

            MyCollections<Integer> collections = new MyCollections<>(list -> 3, CMP);
            collections.sort(toSort);

            assertEquals(expected, toSort);
        }

    }

    @Nested
    class DoubleToIntFunctionFitterTester {

        private static final Integer[] Y = {0, 1, 2, null, null, 5, null, null, null, 9};

        @Test
        void testLinearInterpolation() {
            testFitter(new LinearInterpolation());
        }

        @Test
        void testLinearRegression() {
            testFitter(new LinearRegression());
        }

        private void testFitter(DoubleToIntFunctionFitter fitter) {
            var function = fitter.fitFunction(Y);

            assertEquals(0, function.apply(0.0));
            assertEquals(3, function.apply(0.3));
            assertEquals(6, function.apply(0.7));
            assertEquals(9, function.apply(1.0));
        }

    }

}
