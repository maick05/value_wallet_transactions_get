package br.speck.valuewallet.api.transactions.get.adapter.exception;

import br.speck.valuewallet.api.transactions.get.application.dto.ErrorResponseDTO;
import com.microsoft.azure.functions.HttpStatus;
import java.util.List;

public class BadRequestHttpException extends AbstractHttpException {

    public BadRequestHttpException(List<ErrorResponseDTO> errors) {
        super(HttpStatus.BAD_REQUEST, errors);
    }
}
