package Day_2;

public class StringAnalyzer {
    static String text = "Java is powerful and Java is popular";
    static int totalchars(String text){
        return text.length();
    }
    static int totalwords(String text){
        String trimmedtext=text.trim();
        if(trimmedtext.isEmpty()){
            return 0;
        }
        String[] words=trimmedtext.split("\\s+"); //Purpose: Splits the text into an array of individual words using a Regular Expression (Regex).
        return words.length;
    }
    // Method to count the number of vowels (a, e, i, o, u) in a string
    static int countVowels(String text) {
        // 1. Initialize a counter variable to track the number of vowels
        int count = 0;

        // 2. Loop through each character of the string from index 0 to length - 1
        for (int i = 0; i < text.length(); i++) {

            // 3. Convert the character at index 'i' to lowercase so uppercase vowels are also detected
            char ch = Character.toLowerCase(text.charAt(i));

            // 4. Check if the character matches any of the 5 vowels
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                // 5. Increment the vowel counter by 1
                count++;
            }
        }

        // 6. Return the total vowel count
        return count;
    }
    public static void main(String[] args ){
        System.out.println("total characters in string: "+totalchars(text));
        System.out.println("total no.of words: "+totalwords(text));
        System.out.println("vowel count: "+countVowels(text));

    }

}


//1. Total characters ✅
//2. Number of words ✅
//3. Number of vowels
//4. Number of consonants
//5. Number of spaces
//6. Count occurrences of "Java"
//Expected:
//
//Java appears: 2 times
//7. Convert the sentence to uppercase


