package day03;

import java.util.ArrayList;
import java.util.List;

class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void displayDetails() {
        System.out.println("Name: " + name + ", Salary: " + salary);
    }
}

public class Day3Example2 {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        // Add employees
        employees.add(new Employee("John", 50000));
        employees.add(new Employee("Alice", 60000));
        employees.add(new Employee("Bob", 55000));

        // Loop through and display details
        for (Employee emp : employees) {
            emp.displayDetails();
        }
    }
}
