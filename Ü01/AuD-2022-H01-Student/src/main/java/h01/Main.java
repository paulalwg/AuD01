package h01;

import java.io.*;

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
        System.out.println("Hello World!");


        ListItem<ListItem<Double>> head2 = new ListItem<ListItem<Double>>();  //0
        head2.key = new ListItem<Double> (); head2.key.key = 0.0;



        for ( int i = 0; i<100 ; i++){

            ListItem<Double> headEinzel = new ListItem<Double> ();
            headEinzel.key = 0.0;

            for ( int j = 0; j<10; j++){
                headEinzel.add((double) j);
            }

            head2.add(headEinzel);
        }

        // 20

        ListItem<Double> overlimit = new ListItem<Double>();
        overlimit.key = 10.00;
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(10.00);
        overlimit.add(100.00);  //( 21/ 11)


        head2.add(overlimit);

        int counti =  0 ;

        for ( ListItem<ListItem<Double>> p = head2; p != null; p = p.next){

            counti++;
        }

        System.out.println("counti " + counti);

        System.out.println( "head2 : " + head2.toString());


        for ( int i = 0; i<20 ; i++){

            ListItem<Double> headEinzel = new ListItem<Double> ();
            headEinzel.key = 0.0;
            for ( int j = 0; j<10; j++){
                headEinzel.add((double) j);
            }

            head2.add(headEinzel);
        }

        int count = 0;

        for ( ListItem<ListItem<Double>> p = head2; p != null; p = p.next){

            count++;
        }

        System.out.println(count);

        try {


             // System.out.println("rekursiv as copy : " + DoubleListOfListsProcessor.partitionListsAsCopyRecursively(head2, 90));


           //  System.out.println("iterativ as copy  : " + DoubleListOfListsProcessor.partitionListsAsCopyIteratively(head2,90 ));


           //  System.out.println("in place iterativ : " + DoubleListOfListsProcessor.partitionListsInPlaceIteratively(head2, 90));


         // System.out.println(" rekursivinplace : " + DoubleListOfListsProcessor.partitionListsInPlaceRecursively(head2, 90));


        }

        catch ( Exception e ){

            System.out.println("Exception Message : " + e.getMessage());

        }
        // at 20 , 11 limit by 10



        ListItem<ListItem<Double>> head = new ListItem<ListItem<Double>>();
        ListItem<ListItem<Double>> tail = head;


        ListItem<Double> a1 = new ListItem<Double>();
        a1.key = 1.0;
        ListItem<Double> a2 = new ListItem<Double>();
        a2.key = 3.0;
        ListItem<Double> a3 = new ListItem<Double>();
        a3.key = 5.0;



        ListItem<Double> cHead = new ListItem<>();
        cHead.key = 1.0;
        ListItem<Double> cTail = cHead;

        for (int i = 2; i < 6; i++) {
            cTail.next = new ListItem<Double>();
            cTail = cTail.next;
            cTail.key = (double) i;
        }

        a1.next = a2;
        a2.next = a3;

        head.key = a1;
        ListItem<ListItem<Double>> b = new ListItem<>();
        b.key = null;
        ListItem<ListItem<Double>> c = new ListItem<>();
        c.key = cHead;

        head.next = b;
        b.next = c;


        System.out.println("head" + head.toString());

        ListItem<ListItem<Double>> test = new ListItem<ListItem<Double>> (); test.key = new ListItem<Double> (); test.key.key = 0.0;



        for ( int j = 0; j<10 ; j++) {

            ListItem<Double> e1 = new ListItem<Double>();
            e1.key = 0.0;

            for (int i = 0; i < 5; i ++) {
                e1.add((double) i);
            }

            test.add(e1);

        }
        System.out.println ( "test : " + test);



        /*

        try {

            //  System.out.println("rekursiv as copy : " + DoubleListOfListsProcessor.partitionListsAsCopyRecursively(test, 7));


            // System.out.println("iterativ as copy  : " + DoubleListOfListsProcessor.partitionListsAsCopyIteratively(test,7 ));


            // System.out.println("in place iterativ : " + DoubleListOfListsProcessor.partitionListsInPlaceIteratively(test, 7));


            // System.out.println(" rekursivinplace : " + DoubleListOfListsProcessor.partitionListsInPlaceRecursively(head, 4));


        }

        catch ( Exception e ){

            System.out.println( "Message : " + e.getMessage());
        }

         */


ListItem<ListItem<Double>> headNull = new ListItem<>();
ListItem<Double> item = new ListItem<> (); item.key = 0.0;
headNull.add(item);


// writer reader test
       try (FileWriter writer = new FileWriter ("test13563563");
            BufferedWriter writer2 = new BufferedWriter(writer);) {

           DoubleListOfListsProcessor.write(writer2, headNull);

       }

       catch( Exception ignored){};

       try( FileReader reader = new FileReader("test13563563");
            BufferedReader reader2 = new BufferedReader(reader);){



           System.out.println("result " + DoubleListOfListsProcessor.read(reader2));
       }
       catch(Exception ignored){}











/*
        String s = "1.0#2.0#4.5#3.7#5#6#6.7";

        String[] splitted = s.split("#");

        for ( String sa :splitted){

            System.out.println(sa);

        }

 */


        // System.out.println("rekursiv as copy : " + DoubleListOfListsProcessor.partitionListsAsCopyRecursively(null, 5));


        //System.out.println("iterativ as copy  : " + DoubleListOfListsProcessor.partitionListsAsCopyIteratively(null,4 ));


        //System.out.println("in place iterativ : " + DoubleListOfListsProcessor.partitionListsInPlaceIteratively(null, 5));


        // System.out.println(" rekursivinplace : " + DoubleListOfListsProcessor.partitionListsInPlaceRecursively(null, 5));





        /* ListItem<Double> keyHead = new ListItem<Double > (); keyHead.key = 0.0;
        head.key = keyHead;

        for ( int i = 0; i < 4 ; i++){

            ListItem<Double> headInner = new ListItem<Double> ();
            ListItem<Double> tailInner = headInner ;
            tailInner.key = 1.0;


            for ( int j = 0 ; j < 5; j++){

                tailInner.next = new ListItem<Double>();
                tailInner = tailInner.next;
                tailInner.key = (double) j;

            }

            tail.next = new ListItem<ListItem<Double>> ();
            tail = tail.next;
            tail.key = headInner;



        }

        ListItem<ListItem<Double>> test = new ListItem<>();

        ListItem<Double> a = new ListItem<> (); a.key = 6.0;
        ListItem<Double> b = new ListItem<Double> (); b.key = 6.0;

        a.next = b;
        test.key = a;

        */
    }
}
