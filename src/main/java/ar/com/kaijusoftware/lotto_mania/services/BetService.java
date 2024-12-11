package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.GameBetInfo;
import ar.com.kaijusoftware.lotto_mania.models.Transaction;

import java.util.List;

public interface BetService {
    void performBulkBets(Transaction transaction, List<GameBetInfo> bets);
}
