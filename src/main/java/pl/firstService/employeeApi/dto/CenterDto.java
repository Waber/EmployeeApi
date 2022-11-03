package pl.firstService.employeeApi.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class CenterDto extends RepresentationModel {

    private Long id;
    private String centerName;
    private Long centerCode;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
