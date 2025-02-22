package br.speck.valuewallet.api.transactions.get.adapter.exception;

import br.speck.valuewallet.api.transactions.get.application.constants.ErrorCodes;
import br.speck.valuewallet.api.transactions.get.application.constants.TranslationKeysMap;
import br.speck.valuewallet.api.transactions.get.application.dto.ErrorResponseDTO;
import com.microsoft.azure.functions.HttpStatus;

import java.util.List;
import java.util.Map;

public class NotFoundHttpException extends AbstractHttpException {

    public NotFoundHttpException(String entity, String value) {
        super(HttpStatus.NOT_FOUND,
                List.of(
                        new ErrorResponseDTO(
                                ErrorCodes.NOT_FOUND,
                                "'"+entity+"' '"+value+"' was not found.",
                                Map.of(TranslationKeysMap.ENTITIES, List.of(entity))
                        )
                )
        );
    }

    public NotFoundHttpException(String entity, String prop, String value) {
        super(HttpStatus.NOT_FOUND,
                List.of(
                        new ErrorResponseDTO(
                                ErrorCodes.NOT_FOUND,
                                "'"+entity+"' "+prop+" '"+value+"' was not found.",
                                Map.of(
                                        TranslationKeysMap.ENTITIES, List.of(entity),
                                        TranslationKeysMap.KEYS, List.of(prop)
                                )
                        )
                )
        );
    }
}
