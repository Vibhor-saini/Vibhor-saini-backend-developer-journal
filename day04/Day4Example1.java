package day04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day4Example1  {
    public static void main(String[] args) {
        System.out.println("Hello");

        // =ArratyList============
        // ArrayList<Integer> number = new ArrayList<>();

        // =Set============
        Set<String> names = new HashSet<>();
        names.add("John");
        names.add("Alice");
        names.add("Bob");
        names.add("John");

        System.out.println("Names: " + names);

        // Check if exists
        System.out.println("Contains Alice? " + names.contains("Alice"));

         // Remove
         names.remove("Alice");
        System.out.println("After removing Bob: " + names);
    }
}
