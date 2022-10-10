package pl.firstService.employeeApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterDto extends RepresentationModel {

    private Long id;
    private String centerName;
    private Long centerCode;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
