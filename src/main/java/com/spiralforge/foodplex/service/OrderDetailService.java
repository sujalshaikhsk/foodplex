package com.spiralforge.foodplex.service;

import java.util.List;

import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;

/**
 * OrderDetailService to handle crud operation of order details. 
 */
public interface OrderDetailService {
	
	/**
	 * This method used to save order detail information.
	 * 
	 * @param user
	 * @param orderRequestDto
	 * @return
	 */
	OrderDetail saveOrderDetail(User user, OrderRequestDto orderRequestDto);

	/**
	 * This method used to get all orders for given user. 
	 * 
	 * @param user
	 * @return
	 */
	List<OrderDetail> getAllOrdersByUser(User user);

	/**
	 * This method used to get vendor orders for given user. 
	 * 
	 * @param user
	 * @return
	 */
	List<OrderDetail> getVendorOrders(User user);

}
