package com.jadz.fund.domain.exception;

public final class BadRequestException extends RuntimeException {

	public BadRequestException(final String message) {
		super(message);
	}
}