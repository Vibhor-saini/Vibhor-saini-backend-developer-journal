public class Day07Example2 {
    public static void main(String[] args) {
        try {
            String str = null;
            System.out.println(str.length()); // risky
        } catch (NullPointerException e) {
            System.out.println("Error: Null value found!");
        } catch (Exception e) {
            System.out.println("General error occurred: " + e.getMessage());
        }
    }
}
