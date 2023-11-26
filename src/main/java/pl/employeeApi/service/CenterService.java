package pl.employeeApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.employeeApi.repository.CenterRepository;
import pl.employeeApi.controller.CenterDtoMapper;
import pl.employeeApi.dto.CenterDto;
import pl.employeeApi.center.Center;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;

    public List<CenterDto> getCenters(CenterDtoMapper centerDtoMapper) {
        return centerRepository.findAll().stream()
                .map(centerDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity getCenterById(Long id, CenterDtoMapper centerDtoMapper) {
        Optional<Center> entity = centerRepository.findById(id);
        if (!entity.isPresent()) {
            return new ResponseEntity(String.format("Center with id %s not found", id), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(centerDtoMapper.convertToDto(entity.get()));
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
