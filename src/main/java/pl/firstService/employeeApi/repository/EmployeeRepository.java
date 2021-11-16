package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.firstService.employeeApi.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
