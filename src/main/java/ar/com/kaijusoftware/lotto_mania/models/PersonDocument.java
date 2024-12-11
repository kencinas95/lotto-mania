package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "LM_PERSON_DOCUMENT")
public class PersonDocument {
    @Id
    @Column(name = "PERSON_DOCUMENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Column(name = "DOCUMENT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonDocumentType type;

    @Column(name = "DOCUMENT_VALUE", unique = true, nullable = false)
    private String value;
}