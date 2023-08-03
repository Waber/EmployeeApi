package pl.firstService.employeeApi.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "center_name", nullable = false)
    private String centerName;

    @Column(name = "center_code")
    @Pattern(regexp = "PLC-\\d{5}")
    private Long centerCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centerName", cascade = CascadeType.ALL)
    @Column(name = "employees")
    private List<Employee> employees;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate lastModifiedDate;


}
