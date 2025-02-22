package br.speck.valuewallet.api.transactions.get.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class PaginationDTO {

    @Min(value = 1)
    private Integer pageNumber;

    @Min(value = 1)
    @Max(value = 100)
    private Integer pageSize;

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
