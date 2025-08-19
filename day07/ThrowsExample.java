import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ThrowsExample {

    // Method declares it may throw IOException
    public static String readFile() throws IOException {
        File file = new File("C:\\Dominic\\day06\\employees.txt");
        FileReader reader = new FileReader(file);
        reader.close();
        return "File opened: " + file.getName() + file.getAbsolutePath() + file.getParent();
    }

    public static void main(String[] args) {
        try {
            String message = readFile();  // receive returned value
            System.out.println(message); 
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
