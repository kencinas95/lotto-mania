package ar.com.kaijusoftware.lotto_mania.models;

public enum TransactionStatus {
    PROCESSING,
    WITH_ERRORS,
    WAITING_PAYMENT,
    ON_HOLD,
    VOIDED,
    WAITING_RESULTS,
    COMPLETED,
}
