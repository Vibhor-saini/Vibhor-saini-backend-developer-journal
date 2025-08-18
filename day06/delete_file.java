
package day06;
import java.io.File;
public class delete_file {
    public static void main(String[] args) {
        File file = new File("C:\\\\Dominic\\\\day06\\\\employees.txt");

        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}

