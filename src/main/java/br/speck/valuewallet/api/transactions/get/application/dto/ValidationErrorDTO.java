package br.speck.valuewallet.api.transactions.get.application.dto;

public record ValidationErrorDTO(String propKey, String validationType, Object params) {
}
