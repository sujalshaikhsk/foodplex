package com.spiralforge.foodplex.util;

import com.spiralforge.foodplex.dto.OrderItemDto;

@FunctionalInterface
public interface Validator {
	public Boolean isValid(OrderItemDto order);
}
