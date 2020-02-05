package com.spiralforge.foodplex.service;

import java.util.List;
import java.util.Optional;

import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.exception.InvalidUpiIdException;

public interface UserService {

	OrderDetail placeOrder(Integer userId, OrderRequestDto orderRequestDto) throws InvalidUpiIdException;

	Optional<User> getUserByUserId(Integer userId);

	List<OrderDetail> getOrders(Integer userId);

	List<OrderDetail> getVendorOrders(Integer userId);

}
