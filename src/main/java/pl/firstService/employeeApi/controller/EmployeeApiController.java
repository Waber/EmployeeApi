package pl.firstService.employeeApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.firstService.employeeApi.dto.CreateEmployeeDto;
import pl.firstService.employeeApi.dto.EmployeeAvgSalaryDto;
import pl.firstService.employeeApi.dto.EmployeeResponseDto;
import pl.firstService.employeeApi.dto.EmployeeSumOfSalaryDTO;
import pl.firstService.employeeApi.model.Employee;
import pl.firstService.employeeApi.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeApiController {

    private EmployeeService employeeService;
    private EmployeeDtoMapper employeeDtoMapper;

    @GetMapping()
    public List<EmployeeResponseDto> getEmployess() {
        List<Employee> employees = employeeService.getEmployees();
        return employees.stream()
                .map(employee -> employeeDtoMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseDto getEmployeeById(@PathVariable Long id){
        return employeeDtoMapper.convertToDto(employeeService.getEmployeeById(id));
    }

    @GetMapping("/{employeeId}/history")
    @Operation(summary = "Get changes made to employee")
    public List<EmployeeResponseDto> getEmployeeEditHistory(@PathVariable Long id){
        List<Employee> employees = employeeService.getEmployeeEditHistory(id);
        return employees.stream()
                .map(employee -> employeeDtoMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}/{year}")
    @Operation(summary = "Did the employee worked in this year")
    public ResponseEntity didEmployeeWorkInThisYear(@PathVariable Long id, @DateTimeFormat(pattern = "yyyy") @PathVariable LocalDate year){
        if(!employeeService.existsById(id)){
            return new ResponseEntity(String.format("Employee of id = %s does not exist in the database",id),HttpStatus.BAD_REQUEST);
        }
        if(employeeService.didEmployeeWorkInThisYear(id,year)){
            return ResponseEntity.ok(employeeDtoMapper.convertToEmployedDto(employeeService.getEmployeeById(id),true,year));
        }
        return ResponseEntity.ok(employeeDtoMapper.convertToEmployedDto(employeeService.getEmployeeById(id),false,year));
    }

    @GetMapping("/sum/{employeeId}")
    @Operation(summary = "Return sum of salary received by employee since his work started")
    public EmployeeSumOfSalaryDTO calculateSumOfEmployeeSalary(@PathVariable Long id){
        Employee employee = employeeService.getEmployeeById(id);
        BigDecimal salarySum = employeeService.calculateReceivedSalarySinceWorkBegin(id);
        return employeeDtoMapper.convertToSumOfSalaryDTO(employee,salarySum);
    }

    @GetMapping("/average/{employeeId}")
    @Operation(summary = "Return average salary of employee")
    public EmployeeAvgSalaryDto calculateAverageSalaryOfEmployee(@PathVariable Long id, @PathVariable int year){
        Employee employee = employeeService.getEmployeeById(id);
        BigDecimal averageSalary = employeeService.getAverageEmployeeSalary(id, year);
        return employeeDtoMapper.convertToAvgSalaryDTO(employee,averageSalary);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public List<EmployeeResponseDto> createEmployee(@RequestBody List<CreateEmployeeDto> employeeDto){
        List<Employee> employeesCreated = new ArrayList<>();
        employeeDto.forEach(employeeD -> {
            Employee employee = EmployeeDtoMapper.convertDtoToEntity(employeeD);
            employeesCreated.add(employeeService.addEmployee(employee));});
        return employeeDtoMapper.convertToDtos(employeesCreated);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDto updateEmployeeData(@RequestBody CreateEmployeeDto employeeDto, @PathVariable Long id){
        Employee employee = EmployeeDtoMapper.convertDtoToEntity(employeeDto);
        Employee employeeUpdated = employeeService.updateEmployee(employee,id);
        return employeeDtoMapper.convertToDto(employeeUpdated);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }


}
