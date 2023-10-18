package pl.firstService.employeeApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import pl.firstService.employeeApi.model.Center;

public interface CenterRepository extends  JpaRepository<Center, Long> {
}
