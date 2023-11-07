package pl.firstService.employeeApi.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @Pattern(regexp = "\\d{1,11}")
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

    @Column(name = "salary")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryHistory> salaryHistoryList;

    @Column(name = "job_start_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate jobStartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
    @JoinColumn(name = "centerName")
    private Center centerName;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate lastModifiedDate;


    public enum Position {PROGRAMMER, BUSINESS_ANALYST, PRODUCT_OWNER, MANAGER}


}
