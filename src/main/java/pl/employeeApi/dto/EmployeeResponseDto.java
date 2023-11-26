package pl.employeeApi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.employeeApi.employee.Employee;
import pl.employeeApi.employee.EmployeeValidationResult;
import pl.employeeApi.employee.Position;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class EmployeeResponseDto {


    private Long id;
    private String personalId;
    private String firstName;
    private String lastName;
    private Position position;
    private BigDecimal salary;
    private LocalDate jobStartDate;
    private Long centerId;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
    private EmployeeValidationResult validationResult;

}
