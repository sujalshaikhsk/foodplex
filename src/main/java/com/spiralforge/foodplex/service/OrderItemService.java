package com.spiralforge.foodplex.service;

import java.util.List;

import com.spiralforge.foodplex.dto.OrderItemDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.OrderItem;

/**
 * Order Item service to handle crud operation.
 */
public interface OrderItemService {
	
	/**
	 * This method used to save order Items. 
	 * 
	 * @param orderDetail
	 * @param orderList
	 * @return
	 */
	List<OrderItem> saveOrderItems(OrderDetail orderDetail, List<OrderItemDto> orderList);

}
