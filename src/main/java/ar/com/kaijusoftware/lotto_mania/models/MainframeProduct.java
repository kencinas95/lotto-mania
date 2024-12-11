package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LP_MAINFRAME_PRODUCT")
public class MainframeProduct {
    public static final Map<String, Function<String, ?>> ALLOWED_TYPES = Map.of(
            "str", (value) -> value,
            "int", Integer::valueOf,
            "decimal", Float::valueOf,
            "bool", Boolean::valueOf
    );

    @Id
    @Column(name = "MP_CODE")
    private String code;

    @Column(name = "MP_VALUE")
    private String value;

    @Column(name = "MP_VALUE_TYPE")
    private String type;

    @SuppressWarnings("unchecked")
    public <T extends Object> T get() {
        Function<String, ?> converter = Optional.ofNullable(ALLOWED_TYPES.get(type.toLowerCase()))
                .orElseThrow();
        return (T) converter.apply(value);
    }

}
