package Day_1;

public class FizzBuzz {
    public static  void main (String[] args){
        for (int i = 1; i <= 30; i++) {

            // FizzBuzz
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.print("FizzBuzz");

                // Fizz
            } else if (i % 3 == 0) {
                System.out.print("Fizz");

                // Buzz
            } else if (i % 5 == 0) {
                System.out.print("Buzz");

                // Normal number
            } else {
                System.out.print(i);
            }

            // Even or Odd
            if (i % 2 == 0) {
                System.out.println(" - Even");
            } else {
                System.out.println(" - Odd");
            }

        }

    }
}
