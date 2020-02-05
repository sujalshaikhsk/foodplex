package com.spiralforge.foodplex.exception;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
public class MobileNumberNotValidException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * length of mobile number length is validated.
	 * @param message send an invalid mobile number message .
	 */
	public MobileNumberNotValidException(String message) {
		super(message);
	}
}
