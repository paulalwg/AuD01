package h04;

import h04.collection.MyCollections;
import h04.function.*;
import h04.util.Permutations;
import org.jetbrains.annotations.Nullable;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

/**
 * A sorting experiment to determine the optimal thresholds for {@link MyCollections#sort(List)}.
 *
 * @author Kim Berninger, Nhan Huynh
 */
public final class SortingExperiment {

    /**
     * Don't let anyone instantiate this class.
     */
    private SortingExperiment() {
    }

    /**
     * Main entry point in executing the sorting experiment.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        Integer[][] optimalThreshold = computeOptimalThresholds(1000, 800, 100, 0.5);

        LinearRegression linearRegression = new LinearRegression();
        LinearInterpolation linearInterpolation = new LinearInterpolation();

        ListToIntFunction<Integer> listToIntFunctionRegressionRun = new FunctionOnRatioOfRuns<Integer>(Comparator.naturalOrder(), linearRegression.fitFunction(optimalThreshold[0]));
        ListToIntFunction<Integer> listToIntFunctionInterpolatioRun = new FunctionOnRatioOfRuns<Integer>(Comparator.naturalOrder(), linearInterpolation.fitFunction(optimalThreshold[0]));

        ListToIntFunction<Integer> listToIntFunctionRegressionInversion = new FunctionOnRatioOfInversions<Integer>(Comparator.naturalOrder(), linearRegression.fitFunction(optimalThreshold[1]));
        ListToIntFunction<Integer> listToIntFunctionInterpolatioInversion = new FunctionOnRatioOfInversions<Integer>(Comparator.naturalOrder(), linearInterpolation.fitFunction(optimalThreshold[1]));


        List<ListToIntFunction<Integer>> list = new ArrayList<>();

        list.add(listToIntFunctionRegressionRun);
        list.add(listToIntFunctionInterpolatioRun);
        list.add(listToIntFunctionRegressionInversion);
        list.add(listToIntFunctionInterpolatioInversion);

        double timeRegressionRun = 0;
        double timeInterpolationRun = 0;
        double timeRegressionInversion = 0;
        double timeInterpolationInversion = 0;

        for (int i = 0; i < list.size(); i++) {

            long gesamtZeit = 0;
            Random random = new Random();

            // Gesamtzeit berechnen
            for (int j = 0; j < 500; j++) {

                List<Integer> permutation = new ArrayList<Integer>();

                int length = random.nextInt(1,1001);

                // jeweils eine Permutation mit 1000 Elementen
                for (int k = 0; k < length; k++) {

                    permutation.add(random.nextInt(1, 1001));

                }
                // Messen der Zeit

                ListToIntFunction<Integer> fct = list.get(i);
                MyCollections<Integer> myCollections = new MyCollections<>(fct, Comparator.naturalOrder());

                ThreadMXBean bean = ManagementFactory.getThreadMXBean();
                long startTime = bean.getCurrentThreadCpuTime();

                myCollections.sort(permutation);

                // gesamtzeit errechnen
                gesamtZeit += (bean.getCurrentThreadCpuTime() - startTime);


            }



            //Durchschnitt bestimmen
            if (i == 0)
                timeRegressionRun = (double) Math.round((double)(gesamtZeit / 500) * 100) / 100;

            if (i == 1)
                timeInterpolationRun = (double) Math.round((double)(gesamtZeit / 500) * 100) / 100;

            if (i == 2)
                timeRegressionInversion = (double) Math.round((double)(gesamtZeit / 500) * 100) / 100;

            else {
                timeInterpolationInversion = (double) Math.round((double)(gesamtZeit / 500) * 100) / 100;
            }


        }

        System.out.println("Linear regression " + "ratio of runs" + ": average elapsed time: " + timeRegressionRun +"ms");
        System.out.println("Linear regression " + "ratio of inversions" + ": average elapsed time: " + timeRegressionInversion +"ms");
        System.out.println("Linear interpolation " + "ratio of runs" + ": average elapsed time: " + timeInterpolationRun +"ms");
        System.out.println("Linear interpolation " + "ratio of inversions" + ": average elapsed time: " + timeInterpolationInversion +"ms");








    }



    /**
     * Computes the most optimal threshold for runs and inversions in consideration to the least CPU time.
     *
     * @param n     the length of the list to be sorted to measure the CPU time of the algorithm
     * @param swaps the maximum number of permutations to be performed in order to generate the random permutations
     * @param bins  the number of bins in which the key figures of runs and inversions are to be grouped respectively
     * @param gamma the minimum proportion of the threshold to be tried for a bin should be tried for a bin to determine a valid
     *              result
     * @return the most optimal threshold for runs and inversions
     */
    public static @Nullable Integer[][] computeOptimalThresholds(int n, int swaps, int bins, double gamma) {

        // erstellen der Sequenz
        List<Integer> p = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            p.add(i);
        }


        List<Integer> pReverse = new ArrayList<>();

        for (int i = n; i >= 1; i--) {
            pReverse.add(i);
        }


        // erstellen des Rückgabearray

        Integer[][] result = new Integer[2][bins];


        // erstellen des Dreidimensionalen Arrays zur überprüfung ob eine Umschaltlänge vorkam

        int counter = n;
        int count = 0;

        while (counter == n || counter >= 1) {
            count++;
            counter = counter / 2;
        }

        // das erste Array steht für Runs(0) oder Inversions (1), das zweite für die Bins, das dritte für die Umschaltlängen (0 ist n der letzte index 1)
        boolean[][][] triedUs = new boolean[2][bins][count];


        // setzen des counts wieder auf 0 da wir bei der ersten Umschaltzeit beginnen
        count = 0;

        int u = n;
        Random random = new Random();
        int runs = 0;
        int inversions = 0;


        while (u == n || u >= 1) {


            // erstellen des MyCollection Objektes
            ConstantListToIntFunction<Integer> fct = new ConstantListToIntFunction<>(u);
            MyCollections<Integer> myCollection = new MyCollections<Integer>(fct, Comparator.naturalOrder());

            // swappen, errechnen der Zeit, updaten der Arrays von p
            for (int m = 0; m <= swaps; m++) {

                // random indizes erzeugen
                int i = random.nextInt(0, p.size());
                int j = random.nextInt(0, p.size());

                // falls zufällig i = j
                while (i != j) {
                    i = random.nextInt(0, p.size());
                    j = random.nextInt(0, p.size());
                }

                Collections.swap(p, i, j);

            }


            // Zeit messen p

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();

            long startTime = bean.getCurrentThreadCpuTime();

            // Errechnen des passenden Bins

            runs = Permutations.computeNumberOfRuns(p, Comparator.naturalOrder());
            int kR = (int) Math.floor((double) (bins * (runs - 1)) / n);

            inversions = Permutations.computeNumberOfInversions(p, Comparator.naturalOrder());
            int kI = (int) Math.floor((double) (bins * inversions) / (double) ((n * (n - 1) / 2) + 1));

            myCollection.sort(p);


            long cpuTime = bean.getCurrentThreadCpuTime() - startTime;


            // assert Anweisung dass die Liste in aufsteigender Reihenfolge sortiert ist
            assert isSorted(p, Comparator.naturalOrder());

            // eintragen der Umschaltlänge ins resultArray: bei Gleichstand wird der jetzige Wert übernommen, da die schleife mit der größten
            // Umschaltlänge startet

            if (result[0][kR] == null || result[0][kR] > cpuTime || result[0][kR] == cpuTime)
                result[0][kR] = u;


            triedUs[0][kR][count] = true;


            if (result[1][kI] == null || result[1][kI] > cpuTime || result[1][kI] == cpuTime)
                result[1][kI] = u;


            triedUs[1][kI][count] = true;


            // swappen, errechnen der Zeit, updaten der Arrays von pReverse
            for (int m = 0; m <= swaps; m++) {

                // random indizes erzeugen
                int i = random.nextInt(0, pReverse.size());
                int j = random.nextInt(0, pReverse.size());

                // falls zufällig i = j
                while (i != j) {
                    i = random.nextInt(0, pReverse.size());
                    j = random.nextInt(0, pReverse.size());
                }

                Collections.swap(pReverse, i, j);

            }


            // Zeit messen

            ThreadMXBean beanReverse = ManagementFactory.getThreadMXBean();

            long startTimeReverse = beanReverse.getCurrentThreadCpuTime();

            // Errechnen des passenden Bins


            runs = Permutations.computeNumberOfRuns(pReverse, Comparator.naturalOrder());
            int kRReverse = (int) Math.floor((double) (bins * (runs - 1)) / n);

            inversions = Permutations.computeNumberOfInversions(pReverse, Comparator.naturalOrder());
            int kIReverse = (int) Math.floor((double) (bins * inversions) / (double) ((n * (n - 1) / 2) + 1));

            myCollection.sort(pReverse);


            long cpuTimeReverse = beanReverse.getCurrentThreadCpuTime() - startTimeReverse;


            // assert Anweisung dass die Liste in aufsteigender Reihenfolge sortiert ist
            assert isSorted(pReverse, Comparator.naturalOrder());

            // eintragen der Umschaltlänge ins resultArray: bei Gleichstand wird der jetzige Wert übernommen, da die schleife mit der größten
            // Umschaltlänge startet

            if (result[0][kRReverse] == null || result[0][kRReverse] > cpuTimeReverse || result[0][kRReverse] == cpuTimeReverse)
                result[0][kRReverse] = u;


            triedUs[0][kRReverse][count] = true;


            if (result[1][kIReverse] == null || result[1][kIReverse] > cpuTimeReverse || result[1][kIReverse] == cpuTimeReverse)
                result[1][kIReverse] = u;


            triedUs[1][kIReverse][count] = true;

           // initialisieren der Liste pReverse
            int index = 0;
            for ( int i = pReverse.size(); i >= 1; i --){

                pReverse.set(index, i);
                index++;
            }

            // update des counts
            count++;


            // weiterschalten der Schleife : halbieren der Umschaltlänge
            u = u / 2;


        }


        double mindestAnzahl = (gamma * Math.ceil(Math.log(n))) / Math.log(2);

        // durchgehen für die Runs und Inversions und zählen wie viele Umschaltlängen geprüft wurden
        for (int i = 0; i < 2; i++) {


            for (int k = 0; k < bins; k++) {

                int countUmschaltl = 0;


                // zählen wie viele Umschaltlängen für den bin in betracht gezogen wurden
                for (int j = 0; j < triedUs[0][0].length; j++) {

                    if (triedUs[0][0][i])
                        countUmschaltl++;
                }

                if (countUmschaltl < mindestAnzahl)
                    result[i][k] = null;

            }
        }


        return result;

    }


    /**
     * Returns {@code true} if the list is sorted, {@code false} otherwise.
     *
     * @param list the list to be checked
     * @param cmp  the comparator used to compare elements
     * @param <T>  the type of the elements
     * @return {@code true} if the list is sorted, {@code false} otherwise
     */
    private static <T> boolean isSorted(List<T> list, Comparator<? super T> cmp) {
        Iterator<T> it = list.iterator();
        // Empty list
        if (!it.hasNext()) {
            return true;
        }

        T previous = it.next();
        while (it.hasNext()) {
            T current = it.next();
            if (cmp.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
