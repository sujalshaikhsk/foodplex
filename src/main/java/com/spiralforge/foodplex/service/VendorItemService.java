package com.spiralforge.foodplex.service;

import java.util.List;
import java.util.Optional;

import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.entity.VendorItem;

public interface VendorItemService {
	
	void saveVendorItemDetails(Integer vendorId, VendorItemDto vendorItemDto);

	List<VendorItemDto> getVendorItemDetails();
	
	Optional<VendorItem> getVendorItemById(Integer vendorItemId);
}
