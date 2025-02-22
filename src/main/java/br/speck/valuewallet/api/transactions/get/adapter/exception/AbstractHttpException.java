package br.speck.valuewallet.api.transactions.get.adapter.exception;

import br.speck.valuewallet.api.transactions.get.application.dto.ErrorResponseDTO;
import com.microsoft.azure.functions.HttpStatus;
import java.util.List;

public abstract class AbstractHttpException extends RuntimeException {
    public final HttpStatus status;
    private final List<ErrorResponseDTO> errors;

    protected AbstractHttpException(HttpStatus status, String message, String code) {
        super(message);
        this.status = status;
        this.errors = List.of(new ErrorResponseDTO(code, message));
    }

    protected AbstractHttpException(HttpStatus status, List<ErrorResponseDTO> errors) {
        super(String.join(" | ", errors.stream().map(ErrorResponseDTO::getMessage).toList()));
        this.status = status;
        this.errors = errors;
    }

    public List<ErrorResponseDTO> getErrors() {
        return errors;
    }
}
