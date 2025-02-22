package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.transactions.get.application.dto.GetTransactionDetailsDTO;
import br.speck.valuewallet.api.transactions.get.application.dto.TransactionDetailsResponseDTO;
import br.speck.valuewallet.api.transactions.get.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.transactions.get.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetDetailsTransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private GetDetailsTransactionService sut;

    @Test
    void getDetailsShouldReturnTransactionResponse() throws ClassNotFoundException {
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

        TransactionDetailsResponseDTO expected = new TransactionDetailsResponseDTO(
                entityMock
        );

        // mock
        when(repository.findById(any())).thenReturn(Optional.of(entityMock));

        // Act
        TransactionDetailsResponseDTO actual = sut.execute(new GetTransactionDetailsDTO("any_id"));

        // Capturar parametro
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(repository).findById(captor.capture());

        // asserts
        assertEquals("any_id", captor.getValue(), "Parametro igual ao esperado.");
        assertEquals(expected, actual, "A entidade retornada deve ser igual à entidade esperada.");
    }


    @Test
    public void getDetailsShouldThrowsClassNotFoundException() {
        GetTransactionDetailsDTO paramsDTO = new GetTransactionDetailsDTO("fake_id");

        when(repository.findById(paramsDTO.id())).thenReturn(Optional.empty());

        assertThrows(ClassNotFoundException.class, () -> sut.execute(paramsDTO));
    }
}
