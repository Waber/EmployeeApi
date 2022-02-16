package pl.firstService.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Builder
public class CenterDto {

    private Long id;
    private String centerName;
    private Long centerCode;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
