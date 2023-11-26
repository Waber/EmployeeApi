package pl.employeeApi.controller;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.employeeApi.dto.CreateEmployeeDto;
import pl.employeeApi.dto.EmployeeResponseDto;
import pl.employeeApi.employee.Employee;
import pl.employeeApi.employee.EmployeeValidationResult;
import pl.employeeApi.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeApiController {

    private final EmployeeService employeeService;


    @GetMapping
    @Operation(summary = "Get list of employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity<List<EmployeeResponseDto>> getEmployess() {
        List<EmployeeResponseDto> employees = employeeService.getEmployees();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeResponseDto> entity = employeeService.getEmployeeById(id);

        return ResponseUtil.wrapOrNotFound(entity);
    }


    @PostMapping
    @Operation(summary = "Add employee and create center if it does not exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody CreateEmployeeDto employeeDto) {
        EmployeeResponseDto employee = employeeService.addEmployee(employeeDto);//TODO wywalić maper i wstawić walidacje

        if (!employee.getValidationResult().isValid()){
            return new ResponseEntity(employee.getValidationResult(),HttpStatus.BAD_REQUEST);
        }
        if (employeeService.existsByPersonalId(employee.getPersonalId())){
            return new ResponseEntity(String.format("User with personal Id = %s already exists",employee.getPersonalId()),HttpStatus.BAD_REQUEST);
        }

        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(employee);//24.11 sprawdzić czy działa
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
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@RequestBody CreateEmployeeDto employeeDto, @PathVariable Long id) {
        if (!employeeService.existsById(id)) {
            return new ResponseEntity("Cannot find employee with id = " + id, HttpStatus.NOT_FOUND);
        }
        Employee employeeUpdated = employeeService.updateEmployee(employeeDtoMapper.convertDtoToEntity(employeeDto), id);
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
    public ResponseEntity<EmployeeResponseDto> partiallyUpdateEmployee(@RequestBody CreateEmployeeDto employeeDto, @PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            return new ResponseEntity("Cannot find employee with id = " + id, HttpStatus.NOT_FOUND);
        }
        //TODO dopisac patch, użyć mapstruct z https://kdrozd.pl/how-to-perform-a-partial-update-patch-with-explicit-null/
        Employee employeeUpdated = employeeService.updateEmployee(employeeDtoMapper.convertDtoToEntityPartially(employeeDto), id);
        return ResponseEntity.ok().body(employeeDtoMapper.convertToDto(employeeUpdated));

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }


}
