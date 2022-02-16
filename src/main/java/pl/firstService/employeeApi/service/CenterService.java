package pl.firstService.employeeApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.firstService.employeeApi.model.Center;
import pl.firstService.employeeApi.repository.CenterRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;

    public List<Center> getCenters() {
        return centerRepository.findAll();
    }

    public Center getCenterById(Long id) {
        return centerRepository.findById(id).orElseThrow(NullPointerException::new);
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

    public List<Center> getCenterEditHistory(Long id){
        List<Center> historyList = new ArrayList<>();
        centerRepository.findRevisions(id).get().forEach(x -> {
            x.getEntity().setEditVersion(x.getMetadata());
            historyList.add(x.getEntity());
        });
        return historyList;
    }
}
