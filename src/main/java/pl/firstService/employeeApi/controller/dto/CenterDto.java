package pl.firstService.employeeApi.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CenterDto {

    private Long id;
    private String centerName;
    private Long centerCode;
}
