package com.spiralforge.foodplex.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.OrderItemDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.OrderItem;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);

	@Autowired
	private VendorItemService vendorItemService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	/**
	 * @author Sujal This method is used to place the order to the vendor. After the
	 *         payment done ,save all the items for the order and calculate the
	 *         total price.
	 * 
	 * @param orderDetail is the order details
	 * @param orderList   is the list of items
	 * @return list of order item.
	 */
	@Override
	public List<OrderItem> saveOrderItems(OrderDetail orderDetail, List<OrderItemDto> orderList) {
		logger.info("inside saveOrderItems method");
		List<OrderItem> orderItemlist = null;
		Set<OrderItem> ordeItems = orderList.stream().map(orderItemDto -> {
			OrderItem orderItem = null;
			Optional<VendorItem> vendorItem = vendorItemService.getVendorItemById(orderItemDto.getVendorItemId());
			if (vendorItem.isPresent()) {
				orderItem = new OrderItem();
				BeanUtils.copyProperties(orderItemDto, orderItem);
				BeanUtils.copyProperties(vendorItem.get(), orderItem);
				orderItem.setVendorItem(vendorItem.get());
				orderItem.setOrderDetail(orderDetail);
			}
			return orderItem;
		}).collect(Collectors.toSet());
		if (!ordeItems.isEmpty()) {
			orderItemlist = orderItemRepository.saveAll(ordeItems);
			logger.info("save order items ");
		}
		return orderItemlist;
	}
}
