package pl.firstService.employeeApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.firstService.employeeApi.dto.CenterCreateDto;
import pl.firstService.employeeApi.dto.CenterValidationErrorDto;
import pl.firstService.employeeApi.dto.CreateEmployeeDto;
import pl.firstService.employeeApi.dto.EmployeeResponseDto;
import pl.firstService.employeeApi.model.Employee;
import pl.firstService.employeeApi.service.EmployeeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeApiController {

    private static final String baseEmployeeUrl = "http://localhost:8080/employees/";
    private final EmployeeService employeeService;
    private final EmployeeDtoMapper employeeDtoMapper = new EmployeeDtoMapper();



    @GetMapping()
    @Operation(summary = "Get list of employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public List<EmployeeResponseDto> getEmployess() {
        List<Employee> employees = employeeService.getEmployees();
        return employees.stream()
                .map(employeeDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity getEmployeeById(@PathVariable Long id){
        Optional<Employee> entity = employeeService.getEmployeeById(id);
        if(!entity.isPresent()){
            return new ResponseEntity(String.format("Employee with id %s not found", id), HttpStatus.NOT_FOUND);
        }
        EmployeeResponseDto employeeResponseDto = employeeDtoMapper.convertToDto(entity.get());
        return ResponseEntity.ok(employeeResponseDto);
    }



    @PostMapping()
    @Operation(summary = "Add employee and create center if it does not exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500",description = "Server error", content = @Content)})
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody CreateEmployeeDto employeeDto){
        Employee employee = employeeService.addEmployee(employeeDtoMapper.convertDtoToEntity(employeeDto));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(baseEmployeeUrl+"{id}")
                .buildAndExpand(employee.getId());
        return ResponseEntity.created(uriComponents.toUri())
                .body(employeeDtoMapper.convertToDto(employee));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response ok", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@RequestBody CreateEmployeeDto employeeDto, @PathVariable Long id){
        if (!employeeService.existsById(id)){
            return new ResponseEntity("Cannot find employee with id = " + id, HttpStatus.NOT_FOUND);
        }
        Employee employeeUpdated = employeeService.updateEmployee(employeeDtoMapper.convertDtoToEntity(employeeDto),id);
        return ResponseEntity.ok().body(employeeDtoMapper.convertToDto(employeeUpdated));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially employee data update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response ok", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)})
    public ResponseEntity<EmployeeResponseDto> partiallyUpdateEmployee(@RequestBody CreateEmployeeDto employeeDto, @PathVariable Long id){
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()){
            return new ResponseEntity("Cannot find employee with id = " + id, HttpStatus.NOT_FOUND);
        }
        //TODO dopisac patch, użyć mapstruct z https://kdrozd.pl/how-to-perform-a-partial-update-patch-with-explicit-null/
        Employee employeeUpdated = employeeService.updateEmployee(employeeDtoMapper.convertDtoToEntityPartially(employeeDto),id);
        return ResponseEntity.ok().body(employeeDtoMapper.convertToDto(employeeUpdated));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }


}
