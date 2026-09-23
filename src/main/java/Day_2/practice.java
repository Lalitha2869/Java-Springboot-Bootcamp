package Day_2;
//1 . Method Calling
//public class practice {
//    static int add(int a ,int b){
//        return a+b;
//    }
//    public static void main(String[] args){
//        int result=add(10,3456780);
//        System.out.println(result);
//    }
//}
//main()
// ↓
//add(10,20)
// ↓
//a = 10
//b = 20
//        ↓
//        10 + 20
//        ↓
//        30
//        ↓
//        return 30
//        ↓
//result = 30
//        ↓
//print
// 2. method overloading
//method overloading means having multiple methods with same name but different parameters
//public class practice{
//    static int add(int a, int b){
//        return a+b;
//    }
//    static int add(int x ,int y, int z){
//        return x+y+z;
//    }
//    public static void main(String[] args){
//        int res= add(10,20);
//        int res1=add(10,20,300);
//
//        System.out.println(res);
//        System.out.println(res1);
//
//
//    }
//}

//3.Array:An array stores multiple values of the same type in a fixed-size collection.
//4.String objects are immutable. String operations return new String values rather than modifying the original String object.
//task-1 :Math Problem
//public class practice {
//    static int add(int a, int b) {
//        return a + b;
//    }
//
//    static int sub(int a, int b, int c) {
//        return a - b - c;
//    }
//
//    static int multiply(int a, int b) {
//        return a * b;
//    }
//
//    static boolean isEven(int num) {
//        return num % 2 == 0;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(add(10, 20));
//
//        System.out.println(multiply(5, 4));
//
//        System.out.println(isEven(10));
//
//    }
//}
////Practice 2 — Array
//public  class practice {
//    public static void main(String[] args){
//        int[] numbers={1,2,3,4,5};
//        int sum=0;
//        for(int number:numbers){
//            sum=sum+number;
//
//        }
//    System.out.println(sum);
//
//
//    }
//}

//Practice 3 — String
public class practice{
    public static void main(String[] args){
        String name="Lalitha";
        System.out.println("length : "+name.length());
        System.out.println(("startswith: "+name.startsWith("L")));
        System.out.println("uppercase : "+name.toUpperCase());
        System.out.println("lowecase : "+name.toLowerCase());
        System.out.println("contains : "+name.contains("o"));
        System.out.println("firstchar : "+name.charAt(4));



    }
}