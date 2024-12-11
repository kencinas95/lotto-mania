package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LP_STATIC_DATA")
public class StaticData {
    @Data
    @Embeddable
    @AllArgsConstructor
    public static class StaticDataId {
        @Column(name = "SET_ID")
        private String set;

        @Column(name = "KEY_CODE")
        private String key;
    }

    @EmbeddedId
    private StaticDataId id;

    @Column(name = "DATA_VALUE")
    private String value;

}
