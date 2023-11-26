package pl.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.pl.PESEL;
import org.intellij.lang.annotations.RegExp;
import org.springframework.format.annotation.DateTimeFormat;
import pl.employeeApi.employee.Employee;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class CreateEmployeeDto {
    @PESEL//21.11 sprawdzić czy działa
    private Long personalId;
    private String firstName;
    private String lastName;
    private Employee.Position position;
    private BigDecimal salary;
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate jobStartDate;
    private Long centerId;
}
