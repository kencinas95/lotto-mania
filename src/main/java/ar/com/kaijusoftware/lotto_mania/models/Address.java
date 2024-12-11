package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_ADDRESS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "COUNTRY",
                "STATE",
                "CITY",
                "STREET",
                "POSTAL_CODE",
                "NUMBER_OR_DETAIL"
        })
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADDRESS_ID")
    private Long pk;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "POSTAL_CODE", nullable = false)
    private String postalCode;

    @Column(name = "STREET", nullable = false)
    private String street;

    // NOTE: Do all houses have a number? what about the departments? it has floor and unit number/key
    @Column(name = "NUMBER_OR_DETAIL", nullable = false)
    private String numberOrDetail;

    @With
    @Enumerated(EnumType.STRING)
    @Column(name = "TAG", nullable = false)
    private Tag tag;

}
