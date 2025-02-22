package br.speck.valuewallet.api.transactions.get.adapter.exception;

import br.speck.valuewallet.api.transactions.get.application.constants.ErrorCodes;
import com.microsoft.azure.functions.HttpStatus;

public class InvalidPaginationHttpException extends AbstractHttpException {

    public InvalidPaginationHttpException(String message) {
        super(HttpStatus.BAD_REQUEST, message, ErrorCodes.INVALID_PAGINATION);
    }
}
