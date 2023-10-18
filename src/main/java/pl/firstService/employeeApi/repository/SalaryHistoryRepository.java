package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.firstService.employeeApi.model.SalaryHistory;

import java.util.List;

@Repository
public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Long> {
    List<SalaryHistory> findAllByEmployeeId(Long employeeId);
}
