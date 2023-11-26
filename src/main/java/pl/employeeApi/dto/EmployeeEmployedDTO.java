package pl.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeEmployedDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy")
    private LocalDate askedYear;
    private boolean worked;
}
