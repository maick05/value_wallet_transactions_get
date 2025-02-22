package br.speck.valuewallet.api.transactions.get.application.service;

import br.speck.valuewallet.api.transactions.get.adapter.exception.BadRequestHttpException;

public interface CommandService<Response, ParamsDTO> {
    Response execute(ParamsDTO paramsDTO) throws ClassNotFoundException, BadRequestHttpException;
}
