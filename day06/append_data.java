package day06;
import java.io.FileWriter;
import java.io.IOException;

public class append_data {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("C:\\\\Dominic\\\\day06\\\\employees.txt", true); // true = append mode
            writer.write("105, David Miller\n");
            writer.write("106, Emma Wilson\n");
            writer.close();
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
