package pl.firstService.employeeApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.firstService.employeeApi.dto.EmployeeDto;
import pl.firstService.employeeApi.dto.EmployeeEmployedDTO;
import pl.firstService.employeeApi.model.Employee;
import pl.firstService.employeeApi.service.EmployeeService;

import javax.xml.ws.Response;
import java.text.SimpleDateFormat;
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
    public List<EmployeeDto> getEmployess() {
        List<Employee> employees = employeeService.getEmployees();
        return employees.stream()
                .map(employee -> employeeDtoMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id){
        return employeeDtoMapper.convertToDto(employeeService.getEmployeeById(id));
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Get changes made to employee")
    public List<EmployeeDto> getEmployeeEditHistory(@PathVariable long id){
        List<Employee> employees = employeeService.getEmployeeEditHistory(id);
        return employees.stream()
                .map(employee -> employeeDtoMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/{year}")
    @Operation(summary = "Did the employee worked in this year")
    public ResponseEntity didEmployeeWorkInThisYear(@PathVariable long id, @DateTimeFormat(pattern = "yyyy") @PathVariable LocalDate year){
        if(!employeeService.existsById(id)){
            return new ResponseEntity(String.format("Employee of id = %s does not exist in the database",id),HttpStatus.BAD_REQUEST);
        }
        if(employeeService.didEmployeeWorkInThisYear(id,year)){
            return ResponseEntity.ok(employeeDtoMapper.didEmployeeWorkedInAskedYear(employeeService.getEmployeeById(id),true,year));
        }
        return ResponseEntity.ok(employeeDtoMapper.didEmployeeWorkedInAskedYear(employeeService.getEmployeeById(id),false,year));
    }

    

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public List<EmployeeDto> createEmployee(@RequestBody List<EmployeeDto> employeeDto){
        List<Employee> employeesCreated = new ArrayList<>();
        employeeDto.forEach(employeeD -> {
            Employee employee = EmployeeDtoMapper.convertDtoToEntity(employeeD);
            employeesCreated.add(employeeService.addEmployee(employee));});
        return employeeDtoMapper.convertToDtos(employeesCreated);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployeeData(@RequestBody EmployeeDto employeeDto, @PathVariable long id){
        Employee employee = EmployeeDtoMapper.convertDtoToEntity(employeeDto);
        Employee employeeUpdated = employeeService.updateEmployee(employee,id);
        return employeeDtoMapper.convertToDto(employeeUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
    }


}