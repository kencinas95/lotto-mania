package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "LM_GAME_MODE")
public class GameMode {

    @Id
    @Column(name = "GAME_MODE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;

    @Column(name = "IDENTIFICATION_CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @OneToMany(mappedBy = "mode")
    private Set<GamePlay> gamePlays;

}
