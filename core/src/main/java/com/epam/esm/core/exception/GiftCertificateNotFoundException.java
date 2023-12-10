package com.epam.esm.core.exception;

public class GiftCertificateNotFoundException extends RuntimeException {

    public GiftCertificateNotFoundException(String message) {
        super(message);
    }

    public GiftCertificateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
