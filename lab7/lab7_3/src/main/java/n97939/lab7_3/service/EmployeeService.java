package n97939.lab7_3.service;

import n97939.lab7_3.data.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee getEmployeeById(Long id);

    public Employee getEmployeeByName(String name);

    public List<Employee> getAllEmployees();

    public boolean exists(String email);

    public Employee save(Employee employee);
}
