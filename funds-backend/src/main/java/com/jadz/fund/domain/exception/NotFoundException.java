package com.jadz.fund.domain.exception;

public final class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}