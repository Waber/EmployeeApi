package pl.firstService.employeeApi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.firstService.employeeApi.controller.dto.EmployeeDto;
import pl.firstService.employeeApi.model.Employee;
import pl.firstService.employeeApi.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeApiController {

    private EmployeeService employeeService;

    @GetMapping()
    public List<EmployeeDto> getEmployess() {
        List<Employee> employees = employeeService.getEmployees();
        return employees.stream()
                .map(employee -> EmployeeDtoMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id){
        return EmployeeDtoMapper.convertToDto(employeeService.getEmployeeById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto){
        Employee employee = EmployeeDtoMapper.convertDtotoEntity(employeeDto);
        Employee employeeCreated = employeeService.addEmployee(employee);
        return EmployeeDtoMapper.convertToDto(employeeCreated);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployeeData(@RequestBody EmployeeDto employeeDto, @PathVariable long id){
        Employee employee = EmployeeDtoMapper.convertDtotoEntity(employeeDto);
        Employee employeeUpdated = employeeService.updateEmployee(employee,id);
        return EmployeeDtoMapper.convertToDto(employeeUpdated);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
    }


}
