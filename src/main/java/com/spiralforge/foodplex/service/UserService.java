package com.spiralforge.foodplex.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.exception.InvalidUpiIdException;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
public interface UserService {

	LoginResponseDto userLogin(@Valid LoginRequestDto longinRequestDto) throws MobileNumberNotValidException, UserNotFoundException;

	List<UserResponseDto> vendorList() throws VendorNotFoundException;

	OrderDetail placeOrder(Integer userId, OrderRequestDto orderRequestDto) throws InvalidUpiIdException;

	Optional<User> getUserByUserId(Integer userId);

	List<OrderDetail> getOrders(Integer userId);

	List<OrderDetail> getVendorOrders(Integer userId);
}
