package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import pl.firstService.employeeApi.model.Employee;

public interface EmployeeRepository extends RevisionRepository<Employee,Long, Long>, JpaRepository<Employee, Long> {

}