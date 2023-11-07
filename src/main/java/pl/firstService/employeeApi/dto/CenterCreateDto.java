package pl.firstService.employeeApi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class CenterCreateDto {
    private String centerName;
    private String centerCode;
}
