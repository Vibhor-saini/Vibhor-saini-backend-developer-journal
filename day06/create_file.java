package day06;

import java.io.File;
import java.io.IOException;

public class create_file {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Dominic\\day06\\employees.txt"); 

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred, " + e.getMessage());
            e.printStackTrace();
        }
    }
}

