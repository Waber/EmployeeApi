package pl.firstService.employeeApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.firstService.employeeApi.dto.CenterCreateDto;
import pl.firstService.employeeApi.dto.CenterDto;
import pl.firstService.employeeApi.model.Center;
import pl.firstService.employeeApi.service.CenterService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/center")
public class CenterApiController {
    private CenterDtoMapper centerDtoMapper;
    private CenterService centerService;
    //TODO dopisać validatory

    @GetMapping()
    @Operation(summary = "Get list of centers")
    public List<CenterDto> getCenters(){
        List<Center> centers = centerService.getCenters();
        return centers.stream()
                .map(center -> centerDtoMapper.convertToDto(center))
                .collect(Collectors.toList());
    }

    @GetMapping("/{centerId}")
    public CenterDto getCenterById(@PathVariable Long id){
        return centerDtoMapper.convertToDto(centerService.getCenterById(id));
    }

    @GetMapping("/{centerId}/history")
    public List<CenterDto> getCenterEditHistory(@PathVariable Long id){
        List<Center> centers = centerService.getCenterEditHistory(id);
        return centers.stream()
                .map(center -> centerDtoMapper.convertToDto(center))
                .collect(Collectors.toList());
        //TODO dodać dto z kolumnami dat
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CenterDto createCenter(@RequestBody CenterCreateDto centerDto){
        Center entity = centerDtoMapper.mapCreatedDtoToEntity(centerDto);
        centerService.addCenter(entity);
        return centerDtoMapper.convertToDto(entity);
    }

    @PutMapping("/{centerId}")
    @ResponseStatus(HttpStatus.OK)
    public CenterDto updateCenterData(@RequestBody CenterDto centerDto, @PathVariable Long id){
        Center centerUpdated = centerService.updateCenter(centerDtoMapper.convertDtoToEntity(centerDto),id);
        return centerDtoMapper.convertToDto(centerUpdated);
    }

    @DeleteMapping("/{centerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCenter(@PathVariable Long id){
        centerService.deleteCenter(id);
    }
}
