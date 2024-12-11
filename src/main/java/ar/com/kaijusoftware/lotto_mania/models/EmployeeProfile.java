package ar.com.kaijusoftware.lotto_mania.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_EMPLOYEE_PROFILE")
public class EmployeeProfile {

    @Id
    @Column(name = "EMPLOYEE_PROFILE_ID", unique = true, nullable = false, length = 6)
    private String code;

    @Column(name = "DESCRIPTION", nullable = false, length = 32)
    private String description;

    @OneToMany(mappedBy = "profile")
    private transient Set<Employee> employees;

}
