package pl.employeeApi.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
public class CenterDto extends RepresentationModel {

    private Long id;
    private String centerName;
    private Long centerCode;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
