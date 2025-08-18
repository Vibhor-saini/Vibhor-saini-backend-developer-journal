package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Readfile {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Dominic\\day06\\employes.txt");  
            Scanner reader = new Scanner(file);      // Scanner to read the file

            while (reader.hasNextLine()) {           // loop until end of file
                String data = reader.nextLine();     // read one line
                System.out.println(data);            // print the line
            }

            reader.close(); // close scanner
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            // e.printStackTrace();
        }
    }
}
