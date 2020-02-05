package com.spiralforge.foodplex.service;

import java.util.Optional;

import com.spiralforge.foodplex.entity.VendorItem;

public interface VendorItemService {

	Optional<VendorItem> getVendorItemById(Integer vendorItemId);

}
