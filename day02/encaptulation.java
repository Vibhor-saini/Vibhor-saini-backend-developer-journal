package day02;
import java.util.*;

class Employe {
    private String name;
    private double baseSalary;
    private double bonus;

    // Constructor
    Employe(String name, double baseSalary, double bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    void getBaseSalary() {
        if (baseSalary >= 50000) {
            double after_tax = baseSalary - (baseSalary * 10) / 100;
            System.out.println("Final salary after tax: " + after_tax);
        }
    }

    public double getBonus() {
        return bonus;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary >= 0) { // validation
            this.baseSalary = baseSalary;
        } else {
            System.out.println("Base salary cannot be negative!");
        }
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    // Salary calculation
    public double calculateTotalSalary() {
        return baseSalary + bonus;
    }

    // Display details
    public void displayDetails() {
        double total = calculateTotalSalary();
        System.out.println("Employee Name: " + name);
        System.out.println("Total Salary: " + total);

        if (bonus == 0) {
            System.out.println("No bonus for this employee");
        }
        if (total > 60000) {
            System.out.println("High Earner!");
        }

        getBaseSalary();
    }
}

public class encaptulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Taking input
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Base Salary: ");
        double baseSalary = sc.nextDouble();

        System.out.print("Enter Bonus: ");
        double bonus = sc.nextDouble();

        // Creating object
        Employe emp = new Employe(name, baseSalary, bonus);

        // Changing data using setters
        emp.setBonus(emp.getBonus() + 2000); // Increase bonus by 2000

        // Display details
        emp.displayDetails();

        sc.close();
    }
}
