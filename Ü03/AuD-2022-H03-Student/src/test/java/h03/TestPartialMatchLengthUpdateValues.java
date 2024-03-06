package h03;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPartialMatchLengthUpdateValues {

    enum TestEnum1{
        one,two,three,four,five,six,seve,eight,nine,ten
    }

    EnumIndex<TestEnum1> enumAlphabet = new EnumIndex<>(TestEnum1.class);

    Character[] characters = {'a','b','c','d','e','f','g','h','i','j','k','l'};
    List<Character> characterAlphabet = Arrays.stream(characters).toList();

   SelectionOfCharsIndex selectionOfCharsIndex = new SelectionOfCharsIndex(characterAlphabet);

    UnicodeNumberOfCharIndex unicodeNumberOfCharIndex = new UnicodeNumberOfCharIndex();





   @Test
   public void TestAsMatrix(){

     /*// enumAlphabet

       TestEnum1[] searchStringEnum1 = {TestEnum1.one,TestEnum1.five};

       PartialMatchLengthUpdateValuesAsMatrix<TestEnum1> matrix1 = new PartialMatchLengthUpdateValuesAsMatrix<>(enumAlphabet,searchStringEnum1);

       TestEnum1[] source1 = {TestEnum1.one,TestEnum1.five,TestEnum1.one,TestEnum1.five,TestEnum1.one,TestEnum1.five,TestEnum1.one,TestEnum1.five};

       Integer[] result1 = {1,3,5,7};
      List<Integer> result = Arrays.stream(result1).toList();

      StringMatcher<TestEnum1> stringMatcher1 = new StringMatcher<TestEnum1>(matrix1);

      assertEquals(result,stringMatcher1.findAllMatches(source1));




      // häufig

       TestEnum1[] searchStringEnum2 = {TestEnum1.one};

       PartialMatchLengthUpdateValuesAsMatrix<TestEnum1> matrix2 = new PartialMatchLengthUpdateValuesAsMatrix<>(enumAlphabet,searchStringEnum2);

       TestEnum1[] source2 = {TestEnum1.one,TestEnum1.one,TestEnum1.one,TestEnum1.one,TestEnum1.one,TestEnum1.one,TestEnum1.one,TestEnum1.one};

       Integer[] resulti = {1,2,3,4,5,6,7,8};
       List<Integer> result2 = Arrays.stream(resulti).toList();

       StringMatcher<TestEnum1> stringMatcher2 = new StringMatcher<TestEnum1>(matrix2);

       assertEquals(result2,stringMatcher2.findAllMatches(source2));




       // gar nicht

       TestEnum1[] searchStringEnum3 = {TestEnum1.eight,TestEnum1.seve};

       PartialMatchLengthUpdateValuesAsMatrix<TestEnum1> matrix3 = new PartialMatchLengthUpdateValuesAsMatrix<>(enumAlphabet,searchStringEnum3);

       TestEnum1[] source3 = {TestEnum1.one,TestEnum1.five,TestEnum1.one,TestEnum1.five,TestEnum1.one,TestEnum1.five,TestEnum1.one,TestEnum1.five};

       Integer[] resultiii = {};
       List<Integer> result3 = Arrays.stream(resultiii).toList();

       StringMatcher<TestEnum1> stringMatcher3 = new StringMatcher<TestEnum1>(matrix3);

       assertEquals(result3,stringMatcher3.findAllMatches(source3));





       // selectionOfcharsIndex

       Character[] chars = {'a','b','c'};

       PartialMatchLengthUpdateValuesAsMatrix<Character> matrix11 = new PartialMatchLengthUpdateValuesAsMatrix<Character>(selectionOfCharsIndex,chars);

     Character[] source11 = {'a','b','c','a','b','c','a','b','c'};
       Integer[] resultiii = {1,4,7};
       List<Integer> result11 = Arrays.stream(resultiii).toList();

       StringMatcher<Character> stringMatcher11 = new StringMatcher<Character>(matrix11);

       assertEquals(result11,stringMatcher11.findAllMatches(source11));




       // sehr häufig

       // selectionOfcharsIndex

       Character[] chars12 = {'a','a','a'};

       PartialMatchLengthUpdateValuesAsMatrix<Character> matrix12 = new PartialMatchLengthUpdateValuesAsMatrix<Character>(selectionOfCharsIndex,chars12);

       Character[] source12 = {'a','a','a','a','a','a','a','a','a','a'};
       Integer[] resultiiii = {1,2,3,4,5,6,7,8};
       List<Integer> result12 = Arrays.stream(resultiiii).toList();

       StringMatcher<Character> stringMatcher12 = new StringMatcher<Character>(matrix12);

       assertEquals(result12,stringMatcher12.findAllMatches(source12));




       // gar nicht


       Character[] chars13 = {'b','b','b'};

       PartialMatchLengthUpdateValuesAsMatrix<Character> matrix13 = new PartialMatchLengthUpdateValuesAsMatrix<Character>(selectionOfCharsIndex,chars13);

       Character[] source13 = {'a','a','a','a','a','a','a','a','a','a'};
       Integer[] resultiiiii = {};
       List<Integer> result13 = Arrays.stream(resultiiiii).toList();

       StringMatcher<Character> stringMatcher13 = new StringMatcher<Character>(matrix13);

       assertEquals(result13,stringMatcher13.findAllMatches(source13));

      */



       //Unicode häufig leere source, gar nicht, normal


       // as Automaton gar nicht, häufig, leer

       /*

       Character[] chars12 = {'i','a'};

       PartialMatchLengthUpdateValuesAsAutomaton<Character> matrix12 = new PartialMatchLengthUpdateValuesAsAutomaton<>(unicodeNumberOfCharIndex,chars12);

       Character[] source12 = {};
       Integer[] resultiiii = {};
       List<Integer> result12 = Arrays.stream(resultiiii).toList();

       StringMatcher<Character> stringMatcher12 = new StringMatcher<Character>(matrix12);

       assertEquals(result12,stringMatcher12.findAllMatches(source12));

        */


/*

       // selectionOfcharsIndex automaton häufig, normal gar nicht leer exception

       Character[] chars12 = {};

       PartialMatchLengthUpdateValuesAsAutomaton<Character> matrix12 = new PartialMatchLengthUpdateValuesAsAutomaton<>(selectionOfCharsIndex,chars12);

       Character[] source12 = {};
       Integer[] resultiiii = {};
       List<Integer> result12 = Arrays.stream(resultiiii).toList();

       StringMatcher<Character> stringMatcher12 = new StringMatcher<Character>(matrix12);

       assertEquals(result12,stringMatcher12.findAllMatches(source12));




       // enum automat häufig, gar nicht, normal


       TestEnum1[] searchStringEnum2 = {TestEnum1.two, TestEnum1.one,TestEnum1.one,};

       PartialMatchLengthUpdateValuesAsAutomaton<TestEnum1> matrix2 = new PartialMatchLengthUpdateValuesAsAutomaton<>(enumAlphabet,searchStringEnum2);

       TestEnum1[] source2 = {TestEnum1.one,TestEnum1.three,TestEnum1.one,TestEnum1.one,TestEnum1.one,TestEnum1.two,TestEnum1.one,TestEnum1.one};

       Integer[] resulti = {6};
       List<Integer> result2 = Arrays.stream(resulti).toList();

       StringMatcher<TestEnum1> stringMatcher2 = new StringMatcher<TestEnum1>(matrix2);

       assertEquals(result2,stringMatcher2.findAllMatches(source2));
       
 */



















   }
}
