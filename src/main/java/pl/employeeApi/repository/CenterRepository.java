package pl.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.employeeApi.center.Center;

public interface CenterRepository extends  JpaRepository<Center, Long> {
}
