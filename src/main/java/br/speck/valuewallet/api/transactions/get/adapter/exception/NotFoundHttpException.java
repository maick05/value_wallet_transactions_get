package br.speck.valuewallet.api.transactions.get.adapter.exception;

import br.speck.valuewallet.api.transactions.get.application.constants.ErrorCodes;
import com.microsoft.azure.functions.HttpStatus;

public class NotFoundHttpException extends AbstractHttpException {

    public NotFoundHttpException(String message) {
        super(HttpStatus.NOT_FOUND, message, ErrorCodes.NOT_FOUND);
    }
}
