package br.speck.valuewallet.api.transactions.get.domain.entity.mongodb;

import br.speck.valuewallet.api.transactions.get.domain.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends AbstractMongoDBEntity {
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime transactionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime paymentDate;
    private OperationType operationType;
    private double value;
    private double paidValue;
    private List<String> tags;
    
    public TransactionEntity(String id, String title, String description, LocalDateTime transactionDate, LocalDateTime paymentDate, OperationType operationType, double value, double paidValue, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.transactionDate = transactionDate;
        this.paymentDate = paymentDate;
        this.operationType = operationType;
        this.value = value;
        this.paidValue = paidValue;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public double getValue() {
        return value;
    }

    public double getPaidValue() {
        return paidValue;
    }

    public List<String> getTags() {
        return tags;
    }
}
