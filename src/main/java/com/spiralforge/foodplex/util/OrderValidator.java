package com.spiralforge.foodplex.util;

import com.spiralforge.foodplex.exception.InvalidOrderException;

public interface OrderValidator<E, T> {
	
	Boolean validate(E e, T t) throws InvalidOrderException;
}
