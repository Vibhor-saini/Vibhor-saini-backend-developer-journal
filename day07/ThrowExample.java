// We use throw inside a method to explicitly create an exception.
public class ThrowExample {
    public static void main(String[] args) {
        int age = 15;

        if (age < 18) {
            throw new IllegalArgumentException("Age must be 18 or above!");
        }

        System.out.println("Welcome! You are eligible.");
    }
}
