package pl.employeeApi.employee;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Getter
@Builder
public class EmployeeValidationResult {

    private final Long id;
    private final Collection<EmployeeValidationError> validationErrors;

    public boolean isValid(){
        return validationErrors.isEmpty();
    }
}
