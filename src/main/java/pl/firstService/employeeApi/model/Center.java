package pl.firstService.employeeApi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "center")
@Getter
@Setter
@Audited
public class Center {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "center_name", nullable = false)
    private String centerName;

    @Column(name = "center_code", nullable = false)
    private Long centerCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "center")
    @Column(name = "employees", nullable = false)
    private List<Employee> employees;

    @Column(name = "created_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate createdDate;

    @Column(name = "last_modified_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate lastModifiedDate;

    @Transient
    private RevisionMetadata<Long> editVersion;
}
