package ar.com.kaijusoftware.lotto_mania.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LM_TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", unique = true, nullable = false)
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private String pk;

    @ManyToOne
    @JoinColumn(name = "COMMERCE_ID")
    private Commerce commerce;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @CreationTimestamp
    @Column(name = "STARTED_AT")
    private LocalDateTime startedAt;

    @UpdateTimestamp
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime lastModifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TransactionStatus status;

    @OneToMany(mappedBy = "transaction")
    private transient Set<GameBet> bets;

    @OneToMany(mappedBy = "transaction")
    private Set<TransactionError> errors;

    public boolean hasStatus(final @NonNull TransactionStatus status) {
        return status.equals(this.status);
    }

    public static String generatePk(Long employee, Long customer, Long commerce) {
        final String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        final String time = DateTimeFormatter.ofPattern("HHmmss").format(LocalDateTime.now());
        final String center = String.format("%04d%04d%04d", employee, customer, commerce);
        final int seed = RandomUtils.nextInt(0, 99);

        // {2 digits control}{0-padded 4-spaces [employee, customer, commerce] identifier}
        final String p2 = String.format("%02d%s", seed, center);

        // {2 digits control}{full time hhmmss}
        final String p3 = String.format("%02d%s", seed, time);

        // {2 digits control}{full date yyyymmdd}
        final String p1 = String.format("%02d%s", seed, date);

        return p1 + p2 + p3;
    }

}
