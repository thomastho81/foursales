package br.com.thomas.foursales.infrastructure.exception;

public class OrderUnavailableException extends RuntimeException {

    public OrderUnavailableException(String message) {
        super(message);
    }
}
