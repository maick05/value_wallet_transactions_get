package br.speck.valuewallet.api.transactions.get.domain.enums;

public enum OperationType {
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String description;
    OperationType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
