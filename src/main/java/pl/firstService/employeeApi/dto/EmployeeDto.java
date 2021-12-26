package pl.firstService.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.firstService.employeeApi.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class EmployeeDto {


    private Long id;
    private Long personalId;
    private String firstName;
    private String lastName;
    private Employee.Position position;
    private BigDecimal salary;
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate jobStartDate;
    private String centerName;
}
