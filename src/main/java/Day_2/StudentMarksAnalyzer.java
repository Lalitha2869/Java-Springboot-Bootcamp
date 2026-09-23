package Day_2;

public class StudentMarksAnalyzer {
    static int[] marks = {85, 72, 91, 64, 55, 88, 76, 43};
    static int findhighest(int[] marks){
        int highest=marks[0];
        for(int number:marks){
            if(number>highest){
                highest=number;

            }
        }
        return highest;
    }
    static int findlowest(int[] marks){
        int lowest=marks[0];
        for(int num:marks){
            if(num<lowest){
                lowest=num;
            }
        }
        return lowest;

    }
    static int calculatetotal(int[] marks){
        int total=0;
        for(int j:marks){
            total+=j;
        }
        return  total;

    }
    static double calculateavg(int[] arr) {
        // 1. Return 0 if the array is empty to prevent division by zero
        if (arr == null || arr.length == 0) {
            return 0.0;
        }

        int total = 0;

        // 2. Add up all the numbers in the array
        for (int num : arr) {
            total += num;
        }

        // 3. Cast 'total' to double so division keeps decimals
        return (double) total / arr.length;
    }
    // Count passed students
    static int countPassed(int[] marks) {

        int count = 0;

        for (int mark : marks) {

            if (mark >= 50) {
                count++;
            }
        }

        return count;
    }

    // Count failed students
    static int countFailed(int[] marks) {

        int count = 0;

        for (int mark : marks) {

            if (mark < 50) {
                count++;
            }
        }

        return count;
    }
    static int countAboveAverage(int[] marks) {

        double average = calculateavg(marks);

        int count = 0;

        for (int mark : marks) {

            if (mark > average) {
                count++;
            }
        }

        return count;
    }
        public static void main(String[] args){
        System.out.println("highest: "+findhighest(marks));
        System.out.println("Lowest: "+findlowest(marks));
        System.out.println("Total: "+calculatetotal(marks));
        System.out.println("Average :"+calculateavg(marks));
        System.out.println("Passed   : " + countPassed(marks));
        System.out.println("Failed   : " + countFailed(marks));
        System.out.println("Aboveavgerage: "+countAboveAverage(marks));
    }
}
//Write a program that:
//
//Finds the highest mark
//Finds the lowest mark
//Calculates the total
//Calculates the average
//Counts how many students:
//Passed (>= 50)
//Failed (< 50)
//Finds how many students scored above the average.
//Expected output
//
//Something like:
//
//Highest Mark : 91
//Lowest Mark  : 43
//Total Marks  : 574
//Average      : 71.75
//Passed       : 7
//Failed       : 1
//Above Average: 4