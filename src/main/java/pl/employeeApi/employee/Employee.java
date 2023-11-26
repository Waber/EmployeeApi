package pl.employeeApi.employee;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.PESELValidator;
import org.springframework.format.annotation.DateTimeFormat;
import pl.employeeApi.center.Center;
import pl.employeeApi.salaryHistory.SalaryHistory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "personal_id", nullable = false)
    private String personalId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "monthly_salary")
    private BigDecimal monthlySalary;

    @Column(name = "salaryHistory")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryHistory> salaryHistoryList;

    @Column(name = "job_start_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate jobStartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private Center center;//TODO zmienić na id centrum lub kod centrum. 24.11 zmieniono

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate lastModifiedDate;

    public EmployeeValidationResult validate() {
        Collection<EmployeeValidationError> validationErrors = new ArrayList<>();
        validatePersonalIdNumber().ifPresent(validationErrors::add);

        return new EmployeeValidationResult(id, validationErrors);
    }

    private Optional<EmployeeValidationError> validatePersonalIdNumber() {
        PESELValidator peselValidator = new PESELValidator();
        peselValidator.initialize(null);
        if (!Objects.nonNull(personalId)) {
            return Optional.empty();
        }

        if (peselValidator.isValid(personalId, null)) { //23.11 mam dodany atrybut nullable = false, gdzie to jest walidowane?
            return Optional.empty();
        }


        return Optional.of(EmployeeValidationError.builder()
                .message("Invalid PESEL number")
                .errorCode(EmployeeValidationError.ErrorCode.invalidId)
                .build());
    }

}
