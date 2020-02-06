package com.spiralforge.foodplex.exception;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * if incorrect credentials are given it will throw this exception.
	 * @param message send an invalid credentials message.
	 */
	public UserNotFoundException(String message) {
		super(message);
	}
}
