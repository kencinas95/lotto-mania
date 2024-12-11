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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LP_GAME_BET")
public class GameBet {

    @Id
    @Column(name = "GAME_BET_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PLAY_DETAILS")
    private String play;

    @ManyToOne
    @JoinColumn(name = "GAME_PLAY_ID")
    private GamePlay gamePlay;

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    private Transaction transaction;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;


}
