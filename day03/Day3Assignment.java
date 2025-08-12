package day03;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
 
class Employee {
    private String name;
    private double salary;
 
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
 
    public String getName() {
        return name;
    }
 
    public double getSalary() {
        return salary;
    }
}
 
public class Day3Assignment {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
 
        employees.add(new Employee("Vibhor Saini", 60000));
        employees.add(new Employee("Sugandha Saini", 50000));
        employees.add(new Employee("Gaurav Saini", 58000));
        employees.add(new Employee("Saurab Saini", 45000));
        employees.add(new Employee("Vaibhav Saini", 70000));
 
        System.out.println("Employees with salary above 55,000:");
        int total = 0;
        for (Employee emp : employees) {
            if (emp.getSalary() > 55000) {
                System.out.println(emp.getName() + " - Salary: " + emp.getSalary());
                total++;
            }
        }
        System.out.println("Total employees which have more than 55000 : " + total);
 
        // 3. Highest paid employee
        Employee highest = employees.get(0);
        double high = highest.getSalary();
        // System.out.println(high);
 
        for (Employee emp : employees) {
            if (emp.getSalary() > high) {
                highest = emp;
            }
        }
        System.out.println("Highest Paid: " + highest.getName() + " - " + highest.getSalary());
 
        // 4. Calculate total and average.
        double totall = 0;
        for (Employee emp : employees) {
            totall += emp.getSalary(); // Add current salary to total
        }
        double average = totall / employees.size();
 
        System.out.println("Size " + employees.size());
        System.out.println("Total salary: " + totall);
        System.out.println("Average salary: " + average);
 
        // Sort employees by salary (highest first)
        employees.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
       
        for (Employee emp : employees) {
            System.out.println(emp.getName() + " - " + emp.getSalary());
        }
 
    }
}
 
// 📝 Day 3 Assignment – Employee Manager
// Requirements:
// Store employee data (name, salary) using a List<Employee>.
// Print all employees with salary above 55,000.
// Count how many meet this condition.
// Find the highest-paid employee.
// Calculate total and average salary.
 
// Sort employees by salary (highest first).
 
// Search for an employee by name (input from user).