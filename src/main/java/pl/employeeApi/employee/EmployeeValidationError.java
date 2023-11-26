package pl.employeeApi.employee;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeValidationError {

    private final ErrorCode errorCode;
    private final String message;

    public enum ErrorCode{
        invalidId
    }
}
