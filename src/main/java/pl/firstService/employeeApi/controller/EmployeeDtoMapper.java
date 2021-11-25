package pl.firstService.employeeApi.controller;

import org.modelmapper.ModelMapper;
import pl.firstService.employeeApi.controller.dto.EmployeeDto;
import pl.firstService.employeeApi.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDtoMapper {
    private static ModelMapper modelMapper;

    public EmployeeDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public static Employee convertDtoToEntity(EmployeeDto employeeDto){
        return modelMapper.map(employeeDto, Employee.class);
    }

    public List<EmployeeDto> convertToDtos(Collection<Employee> employees){
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
