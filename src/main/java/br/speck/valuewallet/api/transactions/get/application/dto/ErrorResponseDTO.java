package br.speck.valuewallet.api.transactions.get.application.dto;

import br.speck.valuewallet.api.transactions.get.application.constants.TranslationKeysMap;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;

public class ErrorResponseDTO {
    public final String code;
    private final String message;
    private ValidationErrorDTO validationValues;
    Map<String, List<String>> translationKeys;

    public ErrorResponseDTO(String code, ConstraintViolation<?> violation){
        this.code = code;
        this.validationValues = new ValidationErrorDTO(
                violation.getPropertyPath().toString(),
                violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
                violation.getConstraintDescriptor().getAttributes()
        );
        this.message = violation.getMessage();
        this.translationKeys = Map.of(TranslationKeysMap.KEYS, List.of(violation.getPropertyPath().toString()));
    }

    public ErrorResponseDTO(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ErrorResponseDTO(String code, String message, Map<String, List<String>> translationKeys){
        this.code = code;
        this.message = message;
        this.translationKeys = translationKeys;
    }

    public String getMessage() {
        return message;
    }

    public ValidationErrorDTO getValidationValues() {
        return validationValues;
    }

    public Map<String, List<String>> getTranslationKeys() {
        return translationKeys;
    }
}
