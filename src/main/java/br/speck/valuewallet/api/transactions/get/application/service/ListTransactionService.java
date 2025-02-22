package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.transactions.get.application.dto.ListTransactionDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.TransactionDetailsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListTransactionService extends AbstractService implements CommandService<Page<TransactionDetailsResponseDTO>, ListTransactionDTO> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public Page<TransactionDetailsResponseDTO> execute(ListTransactionDTO paramsDTO) {
        this.validatePagination(paramsDTO.pagination());
        return repository.findAll(paramsDTO.pagination()).map(TransactionDetailsResponseDTO::new);
    }
}