package pl.employeeApi.salaryHistory;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import pl.employeeApi.employee.Employee;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SalaryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal salary;

    private LocalDate changeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_history_id")
    private Employee employee;
}
