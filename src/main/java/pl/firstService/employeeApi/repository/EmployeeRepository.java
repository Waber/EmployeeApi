package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import pl.firstService.employeeApi.model.Employee;

import java.math.BigDecimal;

public interface EmployeeRepository extends  JpaRepository<Employee, Long> {


    @Query("SELECT avg (e.salary) as average_salary from Employee e where e.id=:id and extract (year from e.createdDate)=:year")
    BigDecimal getAverageSalary(@Param("id") Long id, @Param("year") int year);


}
