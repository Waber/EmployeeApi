package pl.firstService.employeeApi.controller;

import org.modelmapper.ModelMapper;
import pl.firstService.employeeApi.controller.dto.EmployeeDto;
import pl.firstService.employeeApi.model.Employee;

public class EmployeeDtoMapper {
    private static ModelMapper modelMapper;

    public static EmployeeDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public static Employee convertDtotoEntity(EmployeeDto employeeDto){
        return modelMapper.map(employeeDto, Employee.class);
    }
}
