package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.firstService.employeeApi.model.Employee;

import java.math.BigDecimal;

public interface EmployeeRepository extends  JpaRepository<Employee, Long> {


}
