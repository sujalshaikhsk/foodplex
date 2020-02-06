package com.spiralforge.foodplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.ResponseDto;
import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.entity.Item;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.Vendor;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.repository.CategoryRepository;
import com.spiralforge.foodplex.repository.ItemRepository;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.repository.VendorItemRepository;
import com.spiralforge.foodplex.repository.VendorRepository;
import com.spiralforge.foodplex.util.ApiConstant;

/**
 * VendorItem Service Implementation.
 */
@Service
public class VendorItemServiceImpl implements VendorItemService {
	
	Logger logger = LoggerFactory.getLogger(VendorItemServiceImpl.class);
	
	@Autowired
	private VendorItemRepository vendorItemRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;
	
	/**
	 * @author Sujal					This method used to get vendor item by Id.  				
	 * 
	 * @param vendorItemId				Vendor ItemId.

	 * @return vendor Item which contains Item, category and price information.
	 */
	@Override
	public Optional<VendorItem> getVendorItemById(Integer vendorItemId) {
		return vendorItemRepository.findById(vendorItemId);
	}
	
	/**
	 * @author Raghavendra.A 			This method used to store add items against particular vendor.
	 * 
	 * @param vendorId		          	is the vendorId.
	 * @param vendorItemDto 			is the vendor Item details.
	 * @throws VendorNotFoundException 
	 * @throws UserNotFoundException 
	 */
	@Override
	public ResponseDto saveVendorItemDetails(Integer userId, VendorItemDto vendorItemDto) throws VendorNotFoundException, UserNotFoundException {			
		if (userId == null) {
			logger.error("User not found");
			throw new UserNotFoundException(ApiConstant.INVALID_USER);
		}
		else {
			VendorItem vendorItem = new VendorItem();
			
			Optional<User> user = userRepository.findById(userId);
			
			if(!user.isPresent()) {
				logger.error("user not found");
				throw new UserNotFoundException(ApiConstant.INVALID_USER);
			}
			
			// get vendor entity
			Optional<Vendor> vendor = vendorRepository.findByUser(user.get());
			
			if(!vendor.isPresent()) {
				logger.error("vendors not found");
				throw new VendorNotFoundException(ApiConstant.VENDOR_NOT_FOUND_EXCEPTION);
			}

			// get item entity
			Item item = itemRepository.getOne(vendorItemDto.getItemId());

			vendorItem.setVendor(vendor.get());
			vendorItem.setItem(item);
			vendorItem.setPrice(vendorItemDto.getPrice());
			
			vendorItemRepository.save(vendorItem);
			
			ResponseDto responseDto = new ResponseDto();
			responseDto.setMessage(ApiConstant.SUCCESS);
			responseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			return responseDto;
		}		
	}
	
	
	/**
	 * @author Raghavendra.A 			This method is used send all the available items to 
	 * 									vendors, so that vendors can add new items. 
	 */
	@Override
	public List<VendorItemDto> getVendorItemDetails() {				
		List<VendorItemDto> dtoList = new ArrayList<>();
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
