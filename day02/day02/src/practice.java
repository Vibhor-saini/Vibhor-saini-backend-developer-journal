import java.util.*;

public class practice {

    public static double calculateTotalSalary(String name, double bonus, double base_salary) {
        double total = bonus + base_salary;
        return total;
    }

    public static void taxDeduction(double total) {
        if (total >= 50000) {
            System.out.println("High Earner! " + total);
            double after_tax = total - (total * 10) / 100;
            System.out.println("Final salary after tax: " + after_tax);
        }
    }

    public static void checkbonus(double bonus, String name) {
        if (bonus == 0) {
            System.out.println("No bonus for " + name);
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your Name: ");

        String name = sc.nextLine();
        System.out.print("Enter your bonus: ");

        double bonus = sc.nextDouble();
        System.out.print("Enter your base_salary: ");

        double base_salary = sc.nextDouble();
        System.out.println(name + " base_salary is : " + calculateTotalSalary(name, bonus, base_salary));
        taxDeduction(calculateTotalSalary(name, bonus, base_salary));
        checkbonus(bonus, name);


    }
}
