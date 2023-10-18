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


    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    public boolean existsById(Long id){
        return employeeRepository.existsById(id);
    }

    public Optional<Employee> findById(Long id){
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee) {
        employee.setCreatedDate(LocalDate.now());
        employee.setLastModifiedDate(LocalDate.now());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Employee employee, long id) {
        employee.setCreatedDate(employeeRepository.getById(id).getCreatedDate());
        employee.setLastModifiedDate(LocalDate.now());
        employee.setId(id);
        return employeeRepository.save(employee);
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
