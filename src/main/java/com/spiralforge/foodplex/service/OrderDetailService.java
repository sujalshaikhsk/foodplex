package com.spiralforge.foodplex.service;

import java.util.List;

import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;

public interface OrderDetailService {

	OrderDetail saveOrderDetail(User user, OrderRequestDto orderRequestDto);

	List<OrderDetail> getAllOrdersByUser(User user);

	List<OrderDetail> getVendorOrders(User user);

}
