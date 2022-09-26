package pl.firstService.employeeApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.firstService.employeeApi.model.Employee;
import pl.firstService.employeeApi.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

//    public EmployeeService(EmployeeRepository repo){
//        employeeRepository = repo;
//    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(NullPointerException::new);
    }

    public boolean existsById(Long id){
        return employeeRepository.existsById(id);
    }

    public Employee addEmployee(Employee employee) {
        employee.setCreatedDate(LocalDate.now());
        employee.setLastModifiedDate(LocalDate.now());
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
                    employee.setLastModifiedDate(LocalDate.now());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    changedEmployee.setId(id);
                    changedEmployee.setCreatedDate(LocalDate.now());
                    changedEmployee.setLastModifiedDate(LocalDate.now());
                    return employeeRepository.save(changedEmployee);
                });
    }


    public boolean didEmployeeWorkInThisYear(Long employeeId, LocalDate year){
        Optional<Employee> emploj = employeeRepository.findById(employeeId);
        if (emploj.get().getJobStartDate().isBefore(year)){
            return true;
        }
        return false;
    }

    public BigDecimal calculateReceivedSalarySinceWorkBegin(Long employeeId){
        Optional<Employee> em = employeeRepository.findById(employeeId);
        long monthsBetween = ChronoUnit.MONTHS.between(
               em.get().getJobStartDate().withDayOfMonth(1)
                ,LocalDate.now().withDayOfMonth(1));
        return em.get().getSalary().multiply(BigDecimal.valueOf(monthsBetween));
    }


    public BigDecimal getAverageEmployeeSalary(Long employeeId, int year){
        return  employeeRepository.getAverageSalary(employeeId, year);
    }


}
