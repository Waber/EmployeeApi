package pl.firstService.employeeApi.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CenterCreateDto {
    private String centerName;
    private Long centerCode;
}
