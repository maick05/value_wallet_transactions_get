package br.speck.valuewallet.api.application.service;

import br.speck.valuewallet.api.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.application.dto.GetTransactionDetailsDto;
import br.speck.valuewallet.api.application.dto.TransactionDetailsResponseDto;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetDetailsTransactionService implements CommandService<TransactionDetailsResponseDto, GetTransactionDetailsDto> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public TransactionDetailsResponseDto execute(GetTransactionDetailsDto paramsDTO) throws ClassNotFoundException {
        Optional<TransactionEntity> transaction = repository.findById(paramsDTO.id());
        if (transaction.isEmpty()) throw new ClassNotFoundException();
        return new TransactionDetailsResponseDto(transaction.get());
    }
}