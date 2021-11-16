package pl.firstService.employeeApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.firstService.employeeApi.repository.EmployeeRepository;
import pl.firstService.employeeApi.model.Employee;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(NullPointerException::new);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Employee changedEmployee, long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setCenter(changedEmployee.getCenter());
                    employee.setFirstName(changedEmployee.getFirstName());
                    employee.setJobStartDate(changedEmployee.getJobStartDate());
                    employee.setLastName(changedEmployee.getLastName());
                    employee.setPersonalId(changedEmployee.getPersonalId());
                    employee.setPosition(changedEmployee.getPosition());
                    employee.setSalary(changedEmployee.getSalary());
                    employee.setJobStartDate(changedEmployee.getJobStartDate());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    changedEmployee.setId(id);
                    return employeeRepository.save(changedEmployee);
                });
    }
}
