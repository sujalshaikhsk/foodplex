package com.spiralforge.foodplex.util;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spiralforge.foodplex.dto.OrderItemDto;
import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.exception.InvalidOrderException;
import com.spiralforge.foodplex.service.UserService;
import com.spiralforge.foodplex.service.VendorItemService;

/**
 * 
 * @author Sujal The booking validator is used to validate the booking
 *         information
 *
 */
@Component("orderValidator")
public class OrderValidatorImpl implements OrderValidator<Integer, OrderRequestDto> {

	Logger logger = LoggerFactory.getLogger(OrderValidatorImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private VendorItemService vendorItemService;

	/**
	 * 
	 */
	@Override
	public Boolean validate(Integer userId, OrderRequestDto orderRequestDto) throws InvalidOrderException {
		if (Objects.isNull(orderRequestDto)) {
			logger.error(ApiConstant.INVALID_ORDER);
			throw new InvalidOrderException(ApiConstant.INVALID_ORDER);
		} else if (orderRequestDto.getOrderList().isEmpty()) {
			logger.error(ApiConstant.ITEM_NOT_FOUND);
			throw new InvalidOrderException(ApiConstant.ITEM_NOT_FOUND);
		} else if (!userService.getUserByUserId(userId).isPresent()) {
			logger.error(ApiConstant.INVALID_USER);
			throw new InvalidOrderException(ApiConstant.INVALID_USER);
		} else if (validateItem(orderRequestDto.getOrderList())) {
			logger.error(ApiConstant.INVALID_ITEM);
			throw new InvalidOrderException(ApiConstant.INVALID_ITEM);
		}

		return true;
	}

	/**
	 * 
	 * @param orderList
	 * @return
	 */
	private boolean validateItem(List<OrderItemDto> orderList) {
		logger.info("inside validate items");

		Optional<Boolean> optionalOrderFlag = orderList.stream().map(order -> {
			if (!vendorItemService.getVendorItemById(order.getVendorItemId()).isPresent())
				return true;
			return false;
		}).filter(itemStatus->itemStatus.equals(true)).findAny();
		
		return (optionalOrderFlag.isPresent() && optionalOrderFlag.get());	
	}

}
