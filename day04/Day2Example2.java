package day04;
import java.util.HashSet;
import java.util.Set;

class Employee {
    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

public class Day2Example2 {
    public static void main(String[] args) {
        Set<Employee> employees = new HashSet<>();

        employees.add(new Employee(101, "John"));
        employees.add(new Employee(102, "Johy"));
        employees.add(new Employee(103, "Johnnn"));
        employees.add(new Employee(104, "Johna"));

        for (Employee emp : employees) {
            System.out.println(emp.getId() + " - " + emp.getName());
        }


    }
}



