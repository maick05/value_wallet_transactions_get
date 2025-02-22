package br.speck.valuewallet.api.transactions.get.application.dto;

import br.speck.valuewallet.api.transactions.get.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.transactions.get.domain.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record TransactionDetailsResponseDTO(
        String id,
        String title,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime transactionDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime paymentDate,
        OperationType operationType,
        double value,
        double paidValue,
        List<String> tags) {

    public TransactionDetailsResponseDTO(TransactionEntity transaction) {
        this(transaction.getId(), transaction.getTitle(), transaction.getDescription(), transaction.getTransactionDate(), transaction.getPaymentDate(), transaction.getOperationType(), transaction.getValue(), transaction.getPaidValue(), transaction.getTags());
    }
}
