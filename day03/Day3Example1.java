package day03;
import java.util.ArrayList;
import java.util.List;

public class Day3Example1 {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();

        // Adding elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Mango");
        fruits.add("Banana"); // duplicate allowed

        // Printing all
        System.out.println("Fruits: " + fruits);

        // Access by index
        System.out.println("First fruit: " + fruits.get(0));

        // Loop through list
        for (String fruit : fruits) {
            System.out.println("Fruit: " + fruit);
        }

        // Remove element
        fruits.remove("Banana");
        System.out.println("After removing Banana: " + fruits);
    }
}
