package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.GameBetInfo;
import ar.com.kaijusoftware.lotto_mania.models.GameBet;
import ar.com.kaijusoftware.lotto_mania.models.Transaction;
import ar.com.kaijusoftware.lotto_mania.models.TransactionStatus;
import ar.com.kaijusoftware.lotto_mania.repositories.GameBetRepository;
import ar.com.kaijusoftware.lotto_mania.repositories.GamePlayRepository;
import ar.com.kaijusoftware.lotto_mania.services.BetService;
import ar.com.kaijusoftware.lotto_mania.services.RulesService;
import ar.com.kaijusoftware.lotto_mania.services.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

    @Value("${app.rules.betValidationRuleGroup:bet-validation}")
    private String betValidationRuleGroupName;

    @Value("${app.transaction.max-bets:4}")
    private int maxBetsPerTransaction;

    private final GameBetRepository gameBetRepository;
    private final GamePlayRepository gamePlayRepository;

    private final TransactionService transactionService;

    private final RulesService rulesService;

    @SneakyThrows
    private Pair<List<GameBet>, List<Throwable>> getValidatedGameBets(final Transaction transaction, final @NonNull List<GameBetInfo> bets) {
        final Map<String, Object> parameters = Map.of(
                "gamePlayRepository", gamePlayRepository,
                "maxBetsPerTransaction", maxBetsPerTransaction,
                "transaction", transaction
        );

        try {
            return rulesService.executeRuleGroup(
                    betValidationRuleGroupName,
                    GameBet.class,
                    parameters,
                    bets);
        } catch (Exception ex) {
            throw new RuntimeException("An error has occurred in validation", ex);
        }
    }

    @Override
    public void performBulkBets(final Transaction transaction, final List<GameBetInfo> betsInfo) {
        log.debug("Performing bulk bet on: {}, {}", transaction, betsInfo);

        try {
            Pair<List<GameBet>, List<Throwable>> results = getValidatedGameBets(transaction, betsInfo);
            if (!results.getSecond().isEmpty()) {
                for (Throwable t : results.getSecond()) {
                    transactionService.addAnonymousError(transaction, t);
                }
            } else {
                gameBetRepository.saveAll(results.getFirst());
                transactionService.changeStatus(transaction, TransactionStatus.WAITING_PAYMENT);
            }
        } catch (Exception ex) {
            log.error("Bets could not be persisted", ex);
            transactionService.addAnonymousError(transaction, ex);
        }
    }
}
