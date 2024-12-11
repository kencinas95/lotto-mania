package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_GAME_PLAY_RESULT")
public class GamePlayResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GAME_PLAY_RESULT_ID")
    private Long pk;

    @ManyToOne
    @JoinColumn(name = "GAME_PLAY_ID", nullable = false)
    private GamePlay gamePlay;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "RESULT_INDEX")
    private Long index;

    @Column(name = "RESULT_VALUE")
    private String value;

}
