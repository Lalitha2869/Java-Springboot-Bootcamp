package Day_3;

//Create a Book class (id, title, author, price) with constructor, getters/setters, toString.	Objects print readable text.

class Book {
    private int id;
    private String title;
    private  String author;
    private double price;
    //constructor
    public Book(int id, String title, String author, double price){
        this.id=id;
        this.title=title;
        this.author=author;
        this.price=price;

    }
    //getter id
    public int getId(){
        return id;


    }
//    setter id
    public void setId(int id){
        this.id=id;

    }
    //GET TITLE
    public String getTitle(){
        return title;
    }
    //set title
    public void setTitle(String title){
        this.title=title;

    }
    //get authour
    public String getAuthor(){
        return  author;
    }
    //set author
    public void setAuthor(String author){
        this.author=author;
    }
    // Getter for price
    public double getPrice(){
        return price;
    }
    //setter for price
    public void setPrice(double price){
        this.price=price;
    }
    //// toString()
    @Override
    public String toString(){
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", author='" + author + '\'' + ", price=" + price + '}';
    }

}
public class BookTask {
    public static void main(String[] args){
        Book book1 = new Book(
                101,
                "Clean Code",
                "Robert C. Martin",
                500.0
        );

        Book book2 = new Book(
                102,
                "Effective Java",
                "Joshua Bloch",
                750.0
        );

        System.out.println(book1);
        System.out.println(book2);

    }
}

