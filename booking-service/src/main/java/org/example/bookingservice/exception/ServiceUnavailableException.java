package org.example.bookingservice.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String msg) {
        super(msg);
    }
}
