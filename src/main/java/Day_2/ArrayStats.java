package Day_2;

public class ArrayStats {

    public static void findArrayStats(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            System.out.println("Array is empty or null.");
            return;
        }

        int max = numbers[0];
        int min = numbers[0];
        long sum = 0;

        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
            sum += num;
        }

        double average = (double) sum / numbers.length;

        System.out.println("Array Elements: " + java.util.Arrays.toString(numbers));
        System.out.println("Maximum Value : " + max);
        System.out.println("Minimum Value : " + min);
        System.out.println("Average Value : " + average);
    }

    public static void main(String[] args) {
        int[] dataset = {15, 42, 8, 93, 27, 64, 5, 81, 33, 72};
        findArrayStats(dataset);
    }
}
