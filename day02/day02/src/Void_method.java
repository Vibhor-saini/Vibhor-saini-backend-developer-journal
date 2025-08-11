import java.util.*;

public class Void_method {

    public static void calcualteSalary(String name, double bonus, double base_salary) {

        double total = bonus + base_salary;
        if (bonus == 0) {
            System.out.println("No bonus for " + name);
        }

        if (total >= 60000) {
            System.out.println("High Earner! " + total);
        }
        System.out.println(name + " total salary is : " + (bonus + base_salary));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your Name: ");

        String name = sc.nextLine();
        System.out.print("Enter your bonus: ");

        double bonus = sc.nextDouble();
        System.out.print("Enter your base_salary: ");

        double base_salary = sc.nextDouble();
        calcualteSalary(name, bonus, base_salary);

    }
}
