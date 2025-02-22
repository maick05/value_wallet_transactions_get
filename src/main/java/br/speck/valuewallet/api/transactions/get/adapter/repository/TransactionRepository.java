package br.speck.valuewallet.api.transactions.get.adapter.repository;

import br.speck.valuewallet.api.transactions.get.domain.entity.mongodb.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {
}

