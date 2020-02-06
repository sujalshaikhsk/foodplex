package com.spiralforge.foodplex.exception;

public class VendorNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * if vendor is not available it will throw this exception.
	 * @param message send an vendor not available message.
	 */
	public VendorNotFoundException(String message) {
		super(message);
	}
}
