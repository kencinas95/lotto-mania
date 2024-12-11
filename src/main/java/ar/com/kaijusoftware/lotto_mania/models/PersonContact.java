package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_PERSON_CONTACT")
public class PersonContact {

    @Id
    @Column(name = "PERSON_CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTACT_TYPE")
    private CustomerContactType type;

    @Column(name = "CONTACT_VALUE", nullable = false, length = 256)
    private String value;

    @Column(name = "IS_MAIN")
    private Boolean isMain;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "TAG")
    private Tag tag;

}
