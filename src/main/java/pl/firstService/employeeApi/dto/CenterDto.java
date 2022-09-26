package pl.firstService.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Builder
public class CenterDto extends RepresentationModel {

    private Long id;
    private String centerName;
    private Long centerCode;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
