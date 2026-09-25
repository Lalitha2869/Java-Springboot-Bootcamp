package Day_4;
//////. List Example (ArrayList)
//////A List allows duplicate elements and keeps them in exact insertion order.
////
////import java.util.List;
////import java.util.ArrayList;
////public class practice {
////    public static void main(String[] args){
////        List<String> names=new ArrayList<>();
////        names.add("lalitha");
////        names.add("ravi");
////        names.add("nikhil");
////        System.out.println("name: "+names);
////        System.out.println("First item(index 0): "+names.get(0));
////    }
////}
////
////2. Set Example (HashSet)
////A Set stores only unique elements. If you add a duplicate, it simply ignores it.
//
////import java.util.HashSet;
////import java.util.Set;
////public class practice {
////    public static void main(String[] args){
////        Set<String> names=new HashSet<>();
////        names.add("lalli");
////        names.add("Rahul");
////        names.add("lalitha");
////        names.add("rani");
////        names.add("lalitha");
////        System.out.println("names :"+names);
//////        System.out.println("");
////    }
////}
//
////3. Map Example (HashMap)
////A Map stores data in Key-Value pairs. Keys must be unique, but values can repeat.
//import java.util.Map;
//import java.util.HashMap;
//public class practice {
//    public static void main(String[] args){
//        Map<Integer, String> names=new HashMap<>();
//        names.put(101,"paradise");
//        names.put(102,"salaar🔥");
//        names.put(103,"darling");
//        System.out.println("names "+names);
//        System.out.println("student with id: "+names.get(102));
//
//    }
//}
//What's <String>?
//
//That's generics.
//
//Generics allow you to specify what type of data a collection should contain.
//class Box<T> {
//    private T value;
//
//    public void setValue(T value) {
//        this.value = value;
//    }
//
//    public T getValue() {
//        return value;
//    }
//
//    // Overriding toString to output custom string format
//    @Override
//    public String toString() {
//        return "Box{value='" + value + "'}";
//    }
//}
//
//public class practice {
//    public static void main(String[] args) {
//        Box<String> box = new Box<>();
//        box.setValue("truglobal");
//
//        // Option 1: Prints using overridden toString()
//        System.out.println("Box: " + box);
//
//        // Option 2: Directly accessing the value via getter
//        System.out.println("Value inside box: " + box.getValue());
//    }
//}
//T means:
//Type will be decided when the object is created.

//An exception is basically:
//
//An event indicating something went wrong during program execution.
//1. Basic try-catch Block
//Catches an exception to prevent program crash when invalid operations occur.
//public class practice{
//    public static void main(String[] args){
//        try {
//            int result=10/0;// Throws ArithmeticException
//            System.out.println("Resulr: "+result);
//        }catch (ArithmeticException e){
//            System.out.println("Error: cannot divided by zero");
//        }
//    }
//}
//2. try-catch-finally
//The finally block always executes, regardless of whether an exception occurred or was caught. It is typically used to clean up resources like files or database connections.


//public class practice {
//    public static void main(String[] args) {
//        try {
//            int[] numbers = {1, 2, 3};
//            System.out.println(numbers[5]); // Throws ArrayIndexOutOfBoundsException
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Error: Invalid array index access!");
//        } finally {
//            System.out.println("Finally block executed: Cleaning up resources.");
//        }
//    }
//}
//3. throw Keyword
//Used to explicitly throw an exception object inside a method when a specific business condition is violated.
//

//public class practice{
//    static void validateAge(int age) {
//        if (age < 18) {
//            throw new IllegalArgumentException("Access denied: Must be at least 18 years old.");
//        } else {
//            System.out.println("Access granted!");
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            validateAge(15);
//        } catch (IllegalArgumentException e) {
//            System.out.println("Caught Exception: " + e.getMessage());
//        }
//    }
//}
//4. throws Keyword
//Declared in the method signature to inform callers that the method might throw a checked exception (e.g., file reading, database operations).

//import java.io.FileReader;
//import java.io.FileNotFoundException;
//
//public class practice {
//    // Declares that this method might throw a FileNotFoundException
//    static void readFile() throws FileNotFoundException {
//        FileReader file = new FileReader("non_existent_file.txt");
//    }
//
//    public static void main(String[] args) {
//        try {
//            readFile();
//        } catch (FileNotFoundException e) {
//            System.out.println("Error: File was not found on disk.");
//        }
//    }
//}

//optional
//it is used to represet a value that may or maynot be present.
//its primary goal is to provide a clean type level solution or handling missing data without throwing a null pointerException
//
//A Lambda Expression is an anonymous function (a function without a name, return type,
// or access modifier) that can be passed around like a variable.

//import java.util.Arrays;
//import java.util.List;
//public class practice{
//    public static void main(String[] args){
//        List<String> names= Arrays.asList("LALLI", "lalitha", "prabhas");
//        names.forEach(name->System.out.println("hello,: "+name));
//
//    }
//
//};
//
//A Stream is a sequence of elements from a data source (like a List or Set)
//that allows you to filter, transform, and process data using pipeline operations.
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class practice {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("lalitha", "rahul", "lalli", "ravi");

        // Processing using Stream API and Lambda Expressions
        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("l"))      // Lambda checks condition
                .map(name -> name.toUpperCase())           // Lambda transforms string
                .collect(Collectors.toList());            // Terminal operation

        System.out.println(filteredNames); // Output: [LALITHA, LALLI]
    }
}

