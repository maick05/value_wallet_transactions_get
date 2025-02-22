package br.speck.valuewallet.api.transactions.get.application.dto;


import org.springframework.data.domain.Pageable;

public record ListTransactionDTO(
        Pageable pagination
) {
}


