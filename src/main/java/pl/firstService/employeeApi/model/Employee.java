package pl.firstService.employeeApi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Audited
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "personal_id", nullable = false)
    private Long personalId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name" ,nullable = false)
    private String lastName;

    //More readability in database with this annotation
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Column(name = "job_start_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate jobStartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", referencedColumnName = "id", nullable = true)
    private Center center;

    @Column(name = "created_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate createdDate;

    @Column(name = "last_modified_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate lastModifiedDate;


    @Transient
    private RevisionMetadata<Long> editVersion;

    public enum Position {PROGRAMMER, BUSINESS_ANALYST, PRODUCT_OWNER, MANAGER}


}
