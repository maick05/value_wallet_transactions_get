package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.transactions.get.application.dto.ListTransactionDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.TransactionDetailsResponseDTO;
import br.speck.valuewallet.api.transactions.get.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.transactions.get.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListTransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private ListTransactionService sut;

    @Test
    void executeShouldReturnListOfTransactionResponse() {
        TransactionEntity entityMock = new TransactionEntity(
                "any_id",
                "Compra no Mercado",
                "Compra de alimentos e itens de limpeza",
                LocalDateTime.now(),
                LocalDateTime.now(),
                OperationType.DEBIT,
                150.0,
                150.0,
                List.of("alimentação", "limpeza")
        );

        TransactionDetailsResponseDTO expectedResp = new TransactionDetailsResponseDTO(entityMock);

        Pageable pageable = PageRequest.of(0, 10);
        Page<TransactionEntity> pageEnt = new PageImpl<>(List.of(entityMock), pageable, 1);
        Page<TransactionDetailsResponseDTO> expected = new PageImpl<>(List.of(expectedResp), pageable, 1);

        when(repository.findAll(pageable)).thenReturn(pageEnt);

        Page<TransactionDetailsResponseDTO> actual = sut.execute(new ListTransactionDTO(pageable));

        assertNotNull(actual, "A entidade retornada não pode ser nula.");
        assertEquals(expected, actual, "A entidade retornada deve ser igual à entidade esperada.");
    }
}
