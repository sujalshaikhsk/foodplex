package com.spiralforge.foodplex.service;

import java.util.List;
import java.util.Optional;

import com.spiralforge.foodplex.dto.ResponseDto;
import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;

/**
 * VendorItem service which contain save and fetch operations of vendor items.
 * 
 * @author Raghavendra A
 *
 */
public interface VendorItemService {
	
	/**
	 * This method used to store add items against particular vendor.
	 * 
	 * @param vendorId
	 * @param vendorItemDto
	 * @return
	 * @throws VendorNotFoundException
	 */
	ResponseDto saveVendorItemDetails(Integer vendorId, VendorItemDto vendorItemDto) throws VendorNotFoundException, UserNotFoundException;

	/**
	 * This method used to store add items against particular vendor.
	 * 
	 * @return			
	 */
	List<VendorItemDto> getVendorItemDetails();
	
	/**
	 * This method used to get vendor item by Id.
	 * 
	 * @param vendorItemId
	 * @return
	 */
	Optional<VendorItem> getVendorItemById(Integer vendorItemId);
}
