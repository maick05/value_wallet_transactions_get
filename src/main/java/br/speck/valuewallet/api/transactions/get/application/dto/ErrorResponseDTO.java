package br.speck.valuewallet.api.transactions.get.application.dto;

import jakarta.validation.ConstraintViolation;

public class ErrorResponseDTO {
    private final String code;
    private final String message;
    private ValidationErrorDTO validationValues;

    public ErrorResponseDTO(String code, ConstraintViolation<?> violation){
        this.code = code;
        this.validationValues = new ValidationErrorDTO(
                violation.getPropertyPath().toString(),
                violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
                violation.getConstraintDescriptor().getAttributes()
        );
        this.message = violation.getMessage();
    }

    public ErrorResponseDTO(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public ValidationErrorDTO getValidationValues() {
        return validationValues;
    }
}
