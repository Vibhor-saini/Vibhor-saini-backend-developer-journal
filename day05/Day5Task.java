package day05;

import java.util.*;

class Product {

    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

public class Day5Task {
    public static void main(String[] args) {
        Map<Integer, Product> ProductMap = new HashMap<>();

        // Adding employees
        ProductMap.put(101, new Product(101, "Keyboard"));
        ProductMap.put(102, new Product(102, "Monitor"));
        ProductMap.put(103, new Product(103, "CPU"));

        //Iterate=================
        // for (Product pro : ProductMap.values()) {
        // System.out.println(pro.getId() + " -> " + pro.getName());
        // }

        // Check if key exists
        System.out.println("Contains ID 101? " + ProductMap.containsKey(102));

        //Remove===============
        ProductMap.remove(103);
        for (Product pro : ProductMap.values()) {
            System.out.println(pro.getId() + " -> " + pro.getName());
        }
    }
}
