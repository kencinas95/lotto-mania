package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID")
    private Long pk;

    @OneToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @OneToMany
    @JoinTable(
            name = "LM_CUSTOMER_ADDRESS",
            joinColumns = @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    )
    private transient Set<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private transient Set<PersonContact> contacts;

    @OneToMany(mappedBy = "customer")
    private transient Set<Transaction> transactions;

}
