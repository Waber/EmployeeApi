package pl.employeeApi.exception;

import org.springframework.security.acls.model.NotFoundException;

public class EmployeeNotFoundException extends NotFoundException {

    public EmployeeNotFoundException(String msg){
        super(msg);
    }

}
