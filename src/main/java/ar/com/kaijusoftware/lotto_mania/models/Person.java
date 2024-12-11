package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_PERSON")
public class Person {

    @Id
    @Column(name = "PERSON_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Column(name = "FIRSTNAME", nullable = false, length = 128)
    private String firstname;

    @Column(name = "SURNAME", nullable = false, length = 128)
    private String surname;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dob;

    @OneToOne
    @JoinColumn(name = "PERSON_DOCUMENT_ID", nullable = false)
    private PersonDocument document;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "LM_PERSON_ADDRESSES",
            joinColumns = @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    )
    private Set<Address> addresses;

    @Transient
    @OneToMany(mappedBy = "person")
    private transient Set<PersonContact> contacts;

}
