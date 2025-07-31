| id | name    | department | salary |
| -- | ------- | ---------- | ------ |
| 1  | Alice   | HR         | 50000  |
| 2  | Bob     | IT         | 70000  |
| 3  | Charlie | IT         | 60000  |



-- Select all employees
SELECT * FROM employees;

-- Employees in IT department
SELECT * FROM employees WHERE department = 'IT';

-- Employees with salary > 60000
SELECT * FROM employees WHERE salary > 60000;

-- Order employees by salary descending
SELECT * FROM employees ORDER BY salary DESC;

-- Count employees per department
SELECT department, COUNT(*) FROM employees GROUP BY department;
