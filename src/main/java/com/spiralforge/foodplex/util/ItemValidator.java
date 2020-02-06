package com.spiralforge.foodplex.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spiralforge.foodplex.dto.OrderItemDto;
import com.spiralforge.foodplex.service.VendorItemService;

@Component
public class ItemValidator implements Validator {

	Logger logger = LoggerFactory.getLogger(ItemValidator.class);

	@Autowired
	private VendorItemService vendorItemService;
	
	@Override
	public Boolean isValid(OrderItemDto order) {
		logger.error("inside item validator");
		return !vendorItemService.getVendorItemById(order.getVendorItemId()).isPresent();
	}

}
