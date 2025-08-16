import java.util.Scanner;

import day04.Employee;

class Employee {
    String name;
    double baseSalary;
    double bonus;

    //Constructor
    Employee(String name, double baseSalary, double bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    // Method to calculate total salary
    double calculateTotalSalary() {
        return baseSalary + bonus;
    }

    void taxDeduction() {
        double total = baseSalary + bonus;
        if (total >= 50000) {
            double after_tax = total - (total * 10) / 100;
            System.out.println("Final salary after tax " + after_tax);
        }
    }


    // Method to display employee info
    void displayDetails() {
        double total = calculateTotalSalary();
        taxDeduction();
        System.out.println("Employee Name: " + name);
        System.out.println("Total Salary: " + total);

        if (bonus == 0) {
            System.out.println("No bonus for this employee");
        }
        if (total > 50000) {
            System.out.print("High Earner!");
        }
    }
}

public class EmployeeMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Taking input
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Base Salary: ");
        double baseSalary = sc.nextDouble();

        System.out.print("Enter Bonus: ");
        double bonus = sc.nextDouble();

        // Creating object og the class
        Employee emp = new Employee(name, baseSalary, bonus);

        // Display details
        emp.displayDetails();
    }
}
