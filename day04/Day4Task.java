package day04;

import java.util.HashSet;
import java.util.Set;

class Product {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class Day4Task {
    public static void main(String[] args) {

        Set<Product> product = new HashSet<>();
        product.add(new Product("Monitor"));
        product.add(new Product("CPU"));
        product.add(new Product("Camera"));
        product.add(new Product("Laptop"));


        Set<String> productName = new HashSet<>();
        productName.add("Monitor");
        productName.add("CPU");
        productName.add("Camera");
        productName.add("Laptop");
        productName.add("Laptop");

        for (String pro : productName) {
            System.out.println(pro);
        }

    }

}

// Day 4 Task
// Create a HashSet of product names.
// Add 5 names (include duplicates).
// Print all products — duplicates should be gone.
