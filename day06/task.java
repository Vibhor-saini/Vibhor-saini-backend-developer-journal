package day06;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class task {
    public static void main(String[] args) {
        try {

            // For create File===============

            File filecreate = new File("C:\\Dominic\\day06\\students.txt");
            if (filecreate.createNewFile()) {
            System.out.println("File created: " + filecreate.getName());
            } else {
            System.out.println("File Exist: " + filecreate.getName());
            }

            // For Write in File=============

            // FileWriter filewrite = new FileWriter("C:\\Dominic\\day06\\students.txt");
            // filewrite.write("101, John Doe, 22\n"); // Writing employee data
            // filewrite.write("102, Alice Smith, 23\n");
            // filewrite.write("103, Bob Johnson, 24\n");
            // System.out.println("Data successfully written to file.");
            // filewrite.close();

            // For Append data in the file=========

            // FileWriter filewrite = new FileWriter("C:\\Dominic\\day06\\students.txt",
            // true);
            // filewrite.write("104, Johny Doe, 22\n"); // Writing employee data
            // filewrite.write("105, Alica Smith, 23\n");
            // filewrite.write("106, Boby Johnson, 24\n");
            // System.out.println("Data append successfully written to file.");
            // filewrite.close();

            // For read data in the file=========

            // File file = new File("C:\\Dominic\\day06\\students.txt");
            // Scanner reader = new Scanner(file); // Scanner to read the file
            // while (reader.hasNextLine()) { // loop until end of file
            // String data = reader.nextLine(); // read one line
            // System.out.println(data); // print the line
            // }
            // reader.close(); // close scanner


            //File delete================
            // File file = new File("C:\\\\Dominic\\\\day06\\\\students.txt");
            // if (file.delete()) {
            //     System.out.println("Deleted the file: " + file.getName());
            // } else {
            //     System.out.println("Failed to delete the file.");
            // }

        } catch (IOException e) {
            System.out.println("An error occured!!");
            e.printStackTrace();
        }
    }
}
