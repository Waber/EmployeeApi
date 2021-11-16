package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.firstService.employeeApi.model.Center;

public interface CenterRepository extends JpaRepository<Center, Long> {
}
