package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "LM_EMPLOYEE")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Column(name = "USERNAME", unique = true, nullable = false, length = 64)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 512)
    private String password;

    @OneToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "PROFILE_ID", nullable = false)
    private EmployeeProfile profile;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime lastModifiedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserSession> sessions;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<Transaction> transactions;

}
