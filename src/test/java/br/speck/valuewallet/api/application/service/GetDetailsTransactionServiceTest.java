package br.speck.valuewallet.api.application.service;

import br.speck.valuewallet.api.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.application.dto.GetTransactionDetailsDto;
import br.speck.valuewallet.api.application.dto.TransactionDetailsResponseDto;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.domain.enums.OperationType;
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

        TransactionDetailsResponseDto expected = new TransactionDetailsResponseDto(
                entityMock
        );

        // mock
        when(repository.findById(any())).thenReturn(Optional.of(entityMock));

        // Act
        TransactionDetailsResponseDto actual = sut.execute(new GetTransactionDetailsDto("any_id"));

        // Capturar parametro
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(repository).findById(captor.capture());

        // asserts
        assertEquals("any_id", captor.getValue(), "Parametro igual ao esperado.");
        assertEquals(expected, actual, "A entidade retornada deve ser igual à entidade esperada.");
    }


    @Test
    public void getDetailsShouldThrowsClassNotFoundException() {
        GetTransactionDetailsDto paramsDTO = new GetTransactionDetailsDto("fake_id");

        when(repository.findById(paramsDTO.id())).thenReturn(Optional.empty());

        assertThrows(ClassNotFoundException.class, () -> sut.execute(paramsDTO));
    }
}
