package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.application.dto.PaginationDTO;
import br.speck.valuewallet.api.transactions.get.application.helper.DTOValidationHelper;
import org.springframework.data.domain.Pageable;

public abstract class AbstractService {
    protected final DTOValidationHelper dtoValidator = new DTOValidationHelper();

    protected void validatePagination(Pageable pageable) {
        PaginationDTO pageDto = new PaginationDTO();
        pageDto.setPageNumber(pageable.getPageNumber());
        pageDto.setPageSize(pageable.getPageSize());
        dtoValidator.validateDTO(pageDto);
    }
}
