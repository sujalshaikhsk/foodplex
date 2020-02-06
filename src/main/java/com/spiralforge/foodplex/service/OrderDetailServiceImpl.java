package com.spiralforge.foodplex.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.repository.OrderDetailRepository;
import com.spiralforge.foodplex.util.Constant;
import com.spiralforge.foodplex.util.Utility;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	/**
	 * @author Sujal
	 *
	 *         Method is used to save the orders for the user.
	 *
	 * @param user            is used to set the user
	 * @param orderRequestDto is the details of orders
	 * @return is the detail of the orders.
	 */
	@Transactional
	@Override
	public OrderDetail saveOrderDetail(User user, OrderRequestDto orderRequestDto) {
		logger.info("inside order place method");
		OrderDetail orderDetail = new OrderDetail();
		BeanUtils.copyProperties(orderRequestDto, orderDetail);
		orderDetail.setTotalPrice(getTotalItemPrice(orderRequestDto));
		orderDetail.setQuantity(getTotalItemQuantity(orderRequestDto));
		orderDetail.setStatus(Constant.CONFIRMED_STATUS);
		orderDetail.setOrderDate(LocalDateTime.now());
		orderDetail.setUser(user);
		orderDetail = orderDetailRepository.save(orderDetail);

		if (!Objects.isNull(orderDetail))
			orderItemService.saveOrderItems(orderDetail, orderRequestDto.getOrderList());
		return orderDetail;
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to count the total price of the selected items.
	 * 
	 * @param orderRequestDto
	 * @return total price
	 */
	private Double getTotalItemPrice(OrderRequestDto orderRequestDto) {
		return orderRequestDto.getOrderList().stream()
				.mapToDouble(ordeItem -> Utility.getTotalPrice(ordeItem.getQuantity(), ordeItem.getPrice())).sum();
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to count the total quantities of the selected items.
	 * 
	 * @param orderRequestDto
	 * @return total quantities
	 */
	private Integer getTotalItemQuantity(OrderRequestDto orderRequestDto) {
		return orderRequestDto.getOrderList().stream().mapToInt(ordeItem -> ordeItem.getQuantity()).sum();
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch all the orders.
	 * 
	 * @param user
	 * @return list of OrderDetail
	 */
	@Override
	public List<OrderDetail> getAllOrdersByUser(User user) {
		return orderDetailRepository.findAllByUser(user);
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch all the orders for vendor.
	 * 
	 * @param user
	 * @return list of OrderDetail
	 */
	@Override
	public List<OrderDetail> getVendorOrders(User user) {
		return orderDetailRepository.getVendorOrders(user);
	}

}
