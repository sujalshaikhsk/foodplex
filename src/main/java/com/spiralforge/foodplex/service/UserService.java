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
	
	/**
	 * This method is used for user login.
	 * 
	 * @param longinRequestDto
	 * @return
	 * @throws MobileNumberNotValidException
	 * @throws UserNotFoundException
	 */
	LoginResponseDto userLogin(@Valid LoginRequestDto longinRequestDto) throws MobileNumberNotValidException, UserNotFoundException;

	/**
	 * This method is used to get list of vendors. 
	 * 
	 * @return
	 * @throws VendorNotFoundException
	 */
	List<UserResponseDto> vendorList() throws VendorNotFoundException;

	/**
	 * This methos is used to place order. 
	 * 
	 * @param userId
	 * @param orderRequestDto
	 * @return
	 * @throws InvalidUpiIdException
	 */
	OrderDetail placeOrder(Integer userId, OrderRequestDto orderRequestDto) throws InvalidUpiIdException;

	/**
	 * This method is used to get user by Id. 
	 * 
	 * @param userId
	 * @return
	 */
	Optional<User> getUserByUserId(Integer userId);
	
	/**
	 * This method is used to get orders for given userdId. 
	 */
	List<OrderDetail> getOrders(Integer userId);

	/**
	 * This method is used to get order detail information for given userId.
	 * @param userId
	 * @return
	 */
	List<OrderDetail> getVendorOrders(Integer userId);
}
