package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.adapter.exception.NotFoundHttpException;
import br.speck.valuewallet.api.transactions.get.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.transactions.get.application.dto.GetTransactionDetailsDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.TransactionDetailsResponseDTO;
import br.speck.valuewallet.api.transactions.get.domain.DomainConstants;
import br.speck.valuewallet.api.transactions.get.domain.entity.mongodb.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetDetailsTransactionService implements CommandService<TransactionDetailsResponseDTO, GetTransactionDetailsDTO> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public TransactionDetailsResponseDTO execute(GetTransactionDetailsDTO paramsDTO) {
        Optional<TransactionEntity> transaction = repository.findById(paramsDTO.id());
        if (transaction.isEmpty())
            throw new NotFoundHttpException(DomainConstants.DOMAIN_KEY_SINGLE, paramsDTO.id());
        return new TransactionDetailsResponseDTO(transaction.get());
    }
}