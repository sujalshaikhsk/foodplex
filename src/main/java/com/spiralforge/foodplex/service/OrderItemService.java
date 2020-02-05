package com.spiralforge.foodplex.service;

import java.util.List;

import com.spiralforge.foodplex.dto.OrderItemDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.OrderItem;

public interface OrderItemService {

	List<OrderItem> saveOrderItems(OrderDetail orderDetail, List<OrderItemDto> orderList);

}
