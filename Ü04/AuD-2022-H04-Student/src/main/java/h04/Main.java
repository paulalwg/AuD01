package h04;

import h04.collection.ListItem;
import h04.collection.MyCollections;
import h04.function.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

        // Test H.1. / 1.2

        int[] elements = {10, 4, 5, 1, 8};

        ArrayDoubleToIntFunction fct = new ArrayDoubleToIntFunction(elements);


        Comparator<Integer> cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {

                return o1.compareTo(o2);

            }
        };


        ConstantListToIntFunction<Integer> fct3 = new ConstantListToIntFunction<>(1);

        /*
        FunctionOnRatioOfRuns<Integer> fctRuns = new FunctionOnRatioOfRuns<Integer>(cmp, fct);

        List<Integer> integers = new LinkedList<>();
        integers.add(10);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(4);





        FunctionOnRatioOfInversions<Integer> fctInversions = new FunctionOnRatioOfInversions<>(cmp, fct);


        System.out.println(fct.apply((double)0.5));


        System.out.println(fctInversions.apply(integers));


         */


// Test H2


        MyCollections<Integer> myCollection = new MyCollections<Integer>(fct3, cmp);

/*
        // test split

        ListItem<Integer> head = null;
        ListItem<Integer> tail = null;


        for (int i = 4; i >= 2; i--) {

            if (head == null) {
                head = tail = new ListItem<>();
                head.key = i;
            } else {

                tail.next = new ListItem<>();
                tail = tail.next;
                tail.key = i;
            }


        }




// test Split
        ListItem<Integer>p = head;

        while( p != null){

            System.out.println("headkeybeforesplir : " +p.key);
            p = p.next;
        }


      ListItem<Integer> rightHead = myCollection.split(head,  2 );


        while( head != null){

            System.out.println("headkey : " +head.key);
            head = head.next;
        }

        while ( rightHead != null){

            System.out.println ("rightHeadKey : " + rightHead.key);
            rightHead = rightHead.next;
        }


 */

/*


        // Test Sort

        List<Integer> list = new LinkedList<>();



        for ( int i = 0; i < 2; i++ ){

            list.add(i);
        }



        for ( int i = 0; i< 2; i++ ){

            list.add(i);
        }



        // 10 9 8 7 6 5 4 3 2


        Iterator<Integer> it1 = list.iterator();

        while ( it1.hasNext()){

            System.out.println( " keybefore " + it1.next());
        }

    myCollection.sort(list);

        Iterator<Integer> it = list.iterator();

        while ( it.hasNext()){

            System.out.println( " key " + it.next());
        }

 */
/*

// Test selectionsort

        ListItem<Integer> head = null;
        ListItem<Integer> tail = null;


        for (int i = 5; i >= 2; i--) {

            if (head == null) {
                head = tail = new ListItem<>();
                head.key = i;
            } else {

                tail.next = new ListItem<>();
                tail = tail.next;
                tail.key = i;
            }


        }


        for (int i = 5; i >= 2; i--) {

            if (head == null) {
                head = tail = new ListItem<>();
                head.key = 2;
            } else {

                tail.next = new ListItem<>();
                tail = tail.next;
                tail.key = 2;
            }


        }

        for (int i = 5; i >= 2; i--) {

            if (head == null) {
                head = tail = new ListItem<>();
                head.key = 1;
            } else {

                tail.next = new ListItem<>();
                tail = tail.next;
                tail.key = 1;
            }
        }


// test selectionsort
            ListItem<Integer> p = head;

            while (p != null) {

                System.out.println("headkeybeforesort : " + p.key);
                p = p.next;
            }


            ListItem<Integer> rightHead = myCollection.selectionSortInPlace(head);


            while (rightHead != null) {

                System.out.println("newheadkey : " + rightHead.key);
                rightHead = rightHead.next;
            }

 */


        /*

        // Test H3 Linearinterpolation

        Integer[] array = {5,5,5,null,null,null,null,-4};

        LinearInterpolation linearInterpolation = new LinearInterpolation();

        DoubleToIntFunction function = linearInterpolation.fitFunction(array);


        int f0 = 5;  int f1 = -4; int x1 = 7; int x0 = 2; int x = 2;

        double interpolation = (double) f0 + ((f1 - f0)/ (x1 -x0)) * (x - x0);

        x = 3;

        double interpolation1 = (double) f0 + ((f1 - f0)/ (x1 -x0)) * (x - x0);

          x = 4;

        double interpolation2 = (double) f0 + ((f1 - f0)/ (x1 -x0)) * (x - x0);

        x = 5;

        double interpolation3 = (double) f0 + ((f1 - f0)/ (x1 -x0)) * (x - x0);


        x = 6;

        double interpolation4 = (double) f0 + ((f1 - f0)/ (x1 -x0)) * (x - x0);

         */

        /*
        LinearRegression linearRegression = new LinearRegression();
        Integer[] array = {10,null,null,10,null};


       ListToIntFunction<Integer> listToIntFunctionRegressionRun = new FunctionOnRatioOfRuns<Integer>(Comparator.naturalOrder(), linearRegression.fitFunction(array));

       MyCollections<Integer> myCollections = new MyCollections<>(listToIntFunctionRegressionRun,Comparator.naturalOrder());

       List<Integer> list = new LinkedList<>();
       list.add(156);
        list.add(629);
        list.add(705);
        list.add(372);
        list.add(689);

        myCollections.sort(list);

         */

            System.out.println("Hello World!");


    }
}






