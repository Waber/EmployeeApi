package pl.firstService.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class EmployeeAvgSalaryDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate jobStartDate;
    private BigDecimal averageSalary;
    private BigDecimal salary;
}
