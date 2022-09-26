package pl.firstService.employeeApi.model;

import lombok.*;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Center {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "center_name", nullable = false)
    private String centerName;

    @Column(name = "center_code")
    private Long centerCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "center", cascade = CascadeType.ALL)
    @Column(name = "employees")
    private List<Employee> employees = new ArrayList<>();

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate lastModifiedDate;

    @Transient
    private RevisionMetadata<Long> editVersion;
}
