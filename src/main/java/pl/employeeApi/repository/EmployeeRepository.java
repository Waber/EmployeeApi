package pl.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.employeeApi.employee.Employee;

import java.util.Optional;

public interface EmployeeRepository extends  JpaRepository<Employee, Long> {

    /**
     * JPA solution for simple check request (https://www.baeldung.com/spring-data-exists-query)
     * @param personalId
     * @return true if user with such personalId is found, false otherwise
     */
    boolean existsByPersonalId(String personalId);

}
