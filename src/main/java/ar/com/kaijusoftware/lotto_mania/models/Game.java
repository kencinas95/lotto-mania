package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_GAME")
public class Game {

    @Id
    @Column(name = "GAME_ID")
    private String code;

    @Column(name = "LABELED_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "GAME_SETTINGS", length = 2048)
    private String settings;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<GameMode> modes;

}
