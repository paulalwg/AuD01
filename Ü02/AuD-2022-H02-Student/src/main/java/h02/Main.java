package h02;


import java.util.LinkedList;

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

        /*

        Integer[] array1 = { 1, 1,1,1, 1, 1, 1,1, 1, 1, 1,  };

        ElementWithIndex<Integer> element1 = new ElementWithIndex<>(8, 1);
        ElementWithIndex<Integer> element2 = new ElementWithIndex<>(8, 1);
        ElementWithIndex<Integer> element3 = new ElementWithIndex<>(8, 0);



        LinkedList<ElementWithIndex<Integer>> list = new LinkedList<>();
        list.add(element1);
        list.add(element2);
        list.add(element3);
        list.add(element3);





        ListOfArrays<Integer> listOfArraysItem1 = new ListOfArrays<>(array1); // ( 0 101 1 2 102 3 4 5 103 104 6 7 8 9 10 )


      try {
          listOfArraysItem1.insert(list.iterator());
      }
      catch (Exception e) {

          System.out.println("message : " + e.getMessage());
      }


        System.out.println("yes");

         */









/*

        LinkedList<Integer>  list = new LinkedList<>();

        for ( int i = 0; i < 2 ; i++)
            list.add(0);

        LinkedList<Integer>  list2 = new LinkedList<>();

        for ( int i = 0; i < 4 ; i++)
            list2.add(100);

        LinkedList<Integer>  list3 = new LinkedList<>();

        for ( int i = 0; i < 3 ; i++)
            list3.add(1000);

        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};


        ListOfArrays<Integer> listOfArrays1 = new ListOfArrays<>(array);




      try{  listOfArrays1.insert(list,6);}

      catch ( Exception e){
          System.out.println(e.getMessage());
      }

        try{  listOfArrays1.insert(list,3);}

        catch ( Exception e){
            System.out.println(e.getMessage());
        }


        try{  listOfArrays1.insert(list2, 5);}

        catch ( Exception e){
            System.out.println(e.getMessage());
        }

        try{  listOfArrays1.insert(list2, 3);}

        catch ( Exception e){
            System.out.println(e.getMessage());
        }

 */






/*
        int indexMinusOne = 5;
        int index = indexMinusOne+1;
        index ++ ;

        System.out.println(indexMinusOne);
        System.out.println(index);

 */





/*

// Iterator test
        Integer[] array = {1, 2,null ,null};


        ListOfArrays<Integer> listOfArrays1 = new ListOfArrays<>(array);
        ListOfArraysIterator<Integer> iterator1 = listOfArrays1.iterator();



        System.out.println(iterator1.next());
        System.out.println(iterator1.hasNext());
        System.out.println(iterator1.next());
        System.out.println(iterator1.hasNext());




        ListOfArraysItem<Integer> item1 = new ListOfArraysItem<>();
        Integer[] array1 = {1, 2, 3, 4, null, null, null};
        item1.array = array1;
        ListOfArraysItem<Integer> item2 = new ListOfArraysItem<>();
        Integer[] array2 = {5, 6, 7, null, null, null, null};
        item2.array = array2;
        ListOfArraysItem<Integer> item3 = new ListOfArraysItem<>();
        Integer[] array3 = {8, 9, 10, 11, 12, 12, null};
        item3.array = array3;




        item1.next = item2;
        item2.next = item3;



       int i = 5;
       int i2 = 3 ;

       int index = -1;


        // hochzählen zum index an dem sich i befindet - i2 steht auf i
        while (i2 < i) {
            index++;
            i2++;
        }

        System.out.println(" index " +index);
        System.out.println(" i2 " +i2);


 */

/*

// test Ü4
Integer[] array2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,22,23,24,25};

ListOfArrays<Integer> listOf = new ListOfArrays<Integer>(array2);

try {
    ListOfArrays<Integer> extract = listOf.extract(1, 2);
}
catch( Exception e){

    System.out.println("Message : " + e.getMessage());
}


ListOfArrays<Integer> extract2 = listOf.extract(5, 9);

ListOfArrays<Integer> extract3 = listOf.extract(6, 6);

        ListOfArrays<Integer> extract4 = listOf.extract(13, 14);


        ListOfArrays<Integer> extract5 = listOf.extract(0, 12);

 */






            System.out.println("Hello World!");
    }
}
