package pl.firstService.employeeApi.dto;

import lombok.Data;
import pl.firstService.employeeApi.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeResponseDto {


    private Long id;
    private Long personalId;
    private String firstName;
    private String lastName;
    private Employee.Position position;
    private BigDecimal salary;
    private LocalDate jobStartDate;
    private String centerName;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
