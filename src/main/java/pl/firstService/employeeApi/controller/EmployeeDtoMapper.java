package pl.firstService.employeeApi.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import pl.firstService.employeeApi.dto.*;
import pl.firstService.employeeApi.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDtoMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public EmployeeResponseDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

    public static Employee convertDtoToEntity(CreateEmployeeDto employeeDto){
        return modelMapper.map(employeeDto, Employee.class);
    }

    public List<EmployeeResponseDto> convertToDtos(Collection<Employee> employees){
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EmployeeEmployedDTO convertToEmployedDto(Employee employee, boolean worked, LocalDate askedYear){
        EmployeeEmployedDTO.EmployeeEmployedDTOBuilder builder = EmployeeEmployedDTO.builder();
        return builder.id(employee.getId())
                .askedYear(askedYear)
                .worked(worked)
                .build();
    }

    public EmployeeSumOfSalaryDTO convertToSumOfSalaryDTO(Employee employee, BigDecimal sumOfSalaryEarned){
        EmployeeSumOfSalaryDTO.EmployeeSumOfSalaryDTOBuilder builder = EmployeeSumOfSalaryDTO.builder();
        return builder
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobStartDate(employee.getJobStartDate())
                .salary(employee.getSalary())
                .amountOfSalaryEarnedSoFar(sumOfSalaryEarned)
                .build();
    }

    public EmployeeAvgSalaryDto convertToAvgSalaryDTO(Employee employee, BigDecimal averageSalary){
        EmployeeAvgSalaryDto.EmployeeAvgSalaryDtoBuilder builder = EmployeeAvgSalaryDto.builder();
        return builder
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobStartDate(employee.getJobStartDate())
                .salary(employee.getSalary())
                .averageSalary(averageSalary)
                .build();
    }
}
