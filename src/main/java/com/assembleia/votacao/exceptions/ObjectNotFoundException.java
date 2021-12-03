package com.assembleia.votacao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String id) {
        super("Objeto não encontrado com ID/CPF: " + id);
    }
}
