package pl.firstService.employeeApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.firstService.employeeApi.model.Center;
import pl.firstService.employeeApi.repository.CenterRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;

    public List<Center> getCenters() {
        return centerRepository.findAll();
    }

    public Optional<Center> getCenterById(Long id) {
        return centerRepository.findById(id);
    }

    public Center addCenter(Center center) {
        center.setCreatedDate(LocalDate.now());
        center.setLastModifiedDate(LocalDate.now());
        return centerRepository.save(center);
    }

    public void deleteCenter(Long id) {
        centerRepository.deleteById(id);
    }

    public Center updateCenter(Center changedCenter, Long id) {
        return centerRepository.findById(id)
                .map(center -> {
                    center.setCenterCode(changedCenter.getCenterCode());
                    center.setCenterName(changedCenter.getCenterName());
                    center.setLastModifiedDate(LocalDate.now());
                    return centerRepository.save(center);
                })
                .orElseGet(() -> {
                    changedCenter.setId(id);
                    changedCenter.setCreatedDate(LocalDate.now());
                    changedCenter.setLastModifiedDate(LocalDate.now());
                    return centerRepository.save(changedCenter);
                });
    }


}
