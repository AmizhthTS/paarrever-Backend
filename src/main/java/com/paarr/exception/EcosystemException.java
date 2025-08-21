package com.paarr.exception;

public class EcosystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int code;

	public EcosystemException(ErrorEnum errorEnum) {
		super(errorEnum.getErrorMessage());
		this.code = errorEnum.getErrorCode();
	}

	public EcosystemException(ErrorEnum errorEnum, String message) {
		super(errorEnum.getErrorMessage() + " :: " + message);
		this.code = errorEnum.getErrorCode();
	}

	public EcosystemException(String message) {
		super(message);
		this.code = 555;
	}

	public int getErrorCode() {
		return this.code;
	}

}
