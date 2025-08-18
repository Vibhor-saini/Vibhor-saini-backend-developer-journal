package day06;

import java.io.FileWriter;
import java.io.IOException;

public class write_data {
    public static void main(String[] args) {
        try {
            // FileWriter to write data into employees.txt
            FileWriter writer = new FileWriter("C:\\\\Dominic\\\\day06\\\\employees.txt");

            writer.write("101, John Doe\n");  // Writing employee data
            writer.write("102, Alice Smith\n");
            writer.write("103, Bob Johnson\n");
            writer.write("104, Vibhor\n");
            writer.close();  // Must close to save data
            System.out.println("Data successfully written to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing.");
            e.printStackTrace();
        }
    }
}
