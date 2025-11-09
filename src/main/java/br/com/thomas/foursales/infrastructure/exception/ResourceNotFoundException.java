package br.com.thomas.foursales.infrastructure.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Recurso de %s não encontrado";

    private static final String MESSAGE_ID = "Recurso de %s não encontrado para o identificador %s";


    public ResourceNotFoundException(String resource) {
        super(String.format(MESSAGE, resource));
    }

    public ResourceNotFoundException(String resource, String id) {
        super(String.format(MESSAGE_ID, resource, id));
    }
}
