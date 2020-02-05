package com.spiralforge.foodplex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.entity.Item;
import com.spiralforge.foodplex.entity.Vendor;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.repository.CategoryRepository;
import com.spiralforge.foodplex.repository.ItemRepository;
import com.spiralforge.foodplex.repository.VendorItemRepository;
import com.spiralforge.foodplex.repository.VendorRepository;

/**
 *	VendorItem Service Implementation
 */
@Service
public class VendorItemServiceImpl implements VendorItemService {

	@Autowired
	VendorItemRepository vendorItemRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	CategoryRepository categoryRepository;	
	
	
	@Override
	public void saveVendorItemDetails(Integer vendorId, VendorItemDto vendorItemDto) {
		VendorItem vendorItem = new VendorItem();
		
		//get vendor entity
		Vendor vendor = vendorRepository.getOne(vendorId);
		
		//get item entity
		Item item = itemRepository.getOne(vendorItemDto.getItemId());
		
		vendorItem.setVendor(vendor);
		vendorItem.setItem(item);
		vendorItem.setPrice(vendorItemDto.getPrice());
		
		vendorItemRepository.save(vendorItem);
	}

	
	@Override
	public List<VendorItemDto> getVendorItemDetails() {				
		List<VendorItemDto> dtoList = new ArrayList<VendorItemDto>();
		List<Item> itemList = itemRepository.findAll();
		itemList.forEach(item -> {
				VendorItemDto vendorItemDto = new VendorItemDto();
				vendorItemDto.setCategoryName(item.getCategory().getCategoryName());
				vendorItemDto.setItemName(item.getItemName());
				vendorItemDto.setItemId(item.getItemId());
				vendorItemDto.setCategoryId(item.getCategory().getCategoryId());
				dtoList.add(vendorItemDto);
				
		 });		
		return dtoList;
	}	
}
