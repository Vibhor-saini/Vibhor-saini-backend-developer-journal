public class Day07Example1 {
    public static void main(String[] args) {
        try {
            int a = 10;
            int b = 0;
            int c = a / b; // risky
            System.out.println("Result: " + c);
        } catch (ArithmeticException e) {
            System.out.println("Error: Cannot divide by zero!");
        } finally {
            System.out.println("Execution finished (finally always runs).");
        }
    }
}

// Note: If you’re not sure what might happen, you can use a general catch:
// catch (Exception e) {
//     System.out.println("Something went wrong: " + e.getMessage());
// }
