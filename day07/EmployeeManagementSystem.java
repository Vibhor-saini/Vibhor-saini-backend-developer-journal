import java.util.*;

class Employee {
    int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}

class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}

public class EmployeeManagementSystem {
    private static Map<Integer, Employee> employees = new HashMap<>();
    public static Employee findEmployee(int id) throws EmployeeNotFoundException {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found!");
        }
        return employees.get(id);
    }

    public static void main(String[] args) {
        employees.put(1, new Employee(1, "Alice", 50000));
        employees.put(2, new Employee(2, "Bob", 60000));

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID to search: ");
        int id = sc.nextInt();

        try {
            Employee emp = findEmployee(id);
            System.out.println("Found: " + emp.name + " with salary " + emp.salary);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Search complete.");
        }
    }
}
