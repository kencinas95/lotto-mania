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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_GAME_PLAY")
public class GamePlay {

    @Id
    @Column(name = "GAME_PLAY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Column(name = "EXTERNAL_PLAY_ID")
    private String externalPlayId;

    @ManyToOne
    @JoinColumn(name = "GAME_MODE_ID")
    private GameMode mode;

    @Column(name = "EXECUTE_AT", nullable = false)
    private LocalDateTime executeAt;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDate expiresAt;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "gamePlay")
    private Set<GamePlayResult> results;

}
