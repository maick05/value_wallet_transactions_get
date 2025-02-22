package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.transactions.get.application.dto.GetTransactionDetailsDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.TransactionDetailsResponseDTO;
import br.speck.valuewallet.api.transactions.get.domain.entity.mongodb.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetDetailsTransactionService implements CommandService<TransactionDetailsResponseDTO, GetTransactionDetailsDTO> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public TransactionDetailsResponseDTO execute(GetTransactionDetailsDTO paramsDTO) throws ClassNotFoundException {
        Optional<TransactionEntity> transaction = repository.findById(paramsDTO.id());
        if (transaction.isEmpty()) throw new ClassNotFoundException();
        return new TransactionDetailsResponseDTO(transaction.get());
    }
}