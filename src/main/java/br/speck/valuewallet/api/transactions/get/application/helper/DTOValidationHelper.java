package br.speck.valuewallet.api.transactions.get.application.helper;

import br.speck.valuewallet.api.transactions.get.adapter.exception.BadRequestHttpException;
import br.speck.valuewallet.api.transactions.get.application.constants.ErrorCodes;
import br.speck.valuewallet.api.transactions.get.application.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DTOValidationHelper {
    private final Validator validator;

    public DTOValidationHelper() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public <T> void validateDTO(T dto) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(dto);
        if (!violations.isEmpty()) {
            List<ErrorResponseDTO> sb = new ArrayList<>();
            for (ConstraintViolation<T> violation : violations)
                sb.add(new ErrorResponseDTO(ErrorCodes.BAD_REQUEST, violation));
            throw new BadRequestHttpException(sb);
        }
    }
}
