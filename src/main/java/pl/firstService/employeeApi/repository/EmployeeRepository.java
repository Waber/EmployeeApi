package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import pl.firstService.employeeApi.model.Employee;

import java.math.BigDecimal;

public interface EmployeeRepository extends RevisionRepository<Employee,Long, Long>, JpaRepository<Employee, Long> {

    @Query("SELECT avg (e.salary) from Employee e where e.id=?1")
    BigDecimal getAverageSalary(Long id);
}
