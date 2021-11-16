package pl.firstService.employeeApi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.firstService.employeeApi.controller.dto.EmployeeDto;
import pl.firstService.employeeApi.model.Employee;
import pl.firstService.employeeApi.service.EmployeeService;

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
    public void deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
    }


}
