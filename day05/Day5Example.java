package day05;

import java.util.HashMap;
import java.util.Map;

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

public class Day5Example {
    public static void main(String[] args) {
        Map<Integer, Employee> employeeMap = new HashMap<>();

        // Adding employees
        employeeMap.put(101, new Employee(101, "John"));
        employeeMap.put(102, new Employee(102, "Alice"));
        employeeMap.put(103, new Employee(103, "Bob"));

        // Access by key
        Employee emp = employeeMap.get(102);
        System.out.println("Employee with ID 102: " + emp.getName());

        // Check if key exists
        System.out.println("Contains ID 101? " + employeeMap.containsKey(101));

        // Remove employee
        employeeMap.remove(103);

        // // Iterate through map
        // for (Map.Entry<Integer, Employee> entry : employeeMap.entrySet()) {
        //     System.out.println(entry.getKey() + " -> " + entry.getValue().getName());
        // }

        for (Employee empp : employeeMap.values()) {
            System.out.println(empp.getId() + " -> " + empp.getName());
        }

    }
}
