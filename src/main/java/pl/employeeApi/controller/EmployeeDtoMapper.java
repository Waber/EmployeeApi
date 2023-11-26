package pl.employeeApi.controller;

import org.modelmapper.ModelMapper;
import pl.employeeApi.dto.*;
import pl.employeeApi.employee.Employee;
import pl.employeeApi.salaryHistory.SalaryHistory;

import java.math.BigDecimal;
import java.time.LocalDate;


public class EmployeeDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public EmployeeResponseDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }


    public Employee convertDtoToEntity(CreateEmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }



//    public List<EmployeeResponseDto> convertToDtos(Collection<Employee> employees){
//        return employees.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    public EmployeeEmployedDTO convertToEmployedDto(Employee employee, boolean worked, LocalDate askedYear) {
        EmployeeEmployedDTO.EmployeeEmployedDTOBuilder builder = EmployeeEmployedDTO.builder();
        return builder.id(employee.getId())
                .askedYear(askedYear)
                .worked(worked)
                .build();
    }

    public EmployeeSumOfSalaryDTO convertToSumOfSalaryDTO(Employee employee, SalaryHistory salaryHistory, BigDecimal sumOfSalaryEarned) {
        EmployeeSumOfSalaryDTO.EmployeeSumOfSalaryDTOBuilder builder = EmployeeSumOfSalaryDTO.builder();
        return builder
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobStartDate(employee.getJobStartDate())
                .salary(employee.getMonthlySalary())
                .amountOfSalaryEarnedSoFar(sumOfSalaryEarned)
                .build();
    }

    public EmployeeAvgSalaryDto convertToAvgSalaryDTO(Employee employee, BigDecimal averageSalary) {
        EmployeeAvgSalaryDto.EmployeeAvgSalaryDtoBuilder builder = EmployeeAvgSalaryDto.builder();
        return builder
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .jobStartDate(employee.getJobStartDate())
                .salary(employee.getMonthlySalary())
                .averageSalary(averageSalary)
                .build();
    }
}
