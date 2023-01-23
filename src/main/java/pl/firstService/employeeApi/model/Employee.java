package pl.firstService.employeeApi.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    private Long personalId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "salary")
    private BigDecimal salary;

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
