package com.spiralforge.foodplex.service;

import java.util.List;

import com.spiralforge.foodplex.dto.VendorItemDto;

public interface VendorItemService {
	
	void saveVendorItemDetails(Integer vendorId, VendorItemDto vendorItemDto);

	List<VendorItemDto> getVendorItemDetails();
	
}
