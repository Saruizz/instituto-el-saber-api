package com.institutosaber.api.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entidad, String campo, Object valor) {
        super(String.format("%s no encontrado con %s: '%s'", entidad, campo, valor));
    }
}
