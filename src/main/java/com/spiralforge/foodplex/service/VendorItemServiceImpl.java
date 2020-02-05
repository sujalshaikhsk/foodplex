package com.spiralforge.foodplex.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.repository.VendorItemRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendorItemServiceImpl implements VendorItemService {
	
	@Autowired
	private VendorItemRepository vendorItemRepository;

	@Override
	public Optional<VendorItem> getVendorItemById(Integer vendorItemId) {
		return vendorItemRepository.findById(vendorItemId);
	}
	
}
