package com.spiralforge.foodplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.ItemCategoryDto;
import com.spiralforge.foodplex.dto.ItemDto;
import com.spiralforge.foodplex.entity.Category;
import com.spiralforge.foodplex.entity.Item;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.repository.CategoryRepository;
import com.spiralforge.foodplex.repository.ItemRepository;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.repository.VendorItemRepository;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	VendorItemRepository vendorItemRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemRepository itemRepository;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of categories are fetched from
	 *        database.
	 * @return List of categories are fetched.
	 */
	@Override
	public List<ItemCategoryDto> getItemCategoryListByVendorId(Integer userId) {
		logger.info("got the list of categories");
		User user = userRepository.findByUserId(userId);
		List<VendorItem> vendorItemList = vendorItemRepository.findVendorItemByUser(user);

		List<Item> itemList = vendorItemList.stream().map(item -> item.getItem()).collect(Collectors.toList());
		List<Category> categoryList = vendorItemList.stream().map(item -> item.getItem().getCategory())
				.collect(Collectors.toList());

		List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
		categoryList.forEach(category -> {
			ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
			itemCategoryDto.setCategoryName(category.getCategoryName());

			List<ItemDto> itemDtos = itemList.stream()
					.filter(item -> category.getCategoryId().equals(item.getCategory().getCategoryId()))
					.map(item -> convertEntityToDto(item)).collect(Collectors.toList());
			itemCategoryDto.setItemList(itemDtos);
			itemCategoryDtos.add(itemCategoryDto);

		});

		return itemCategoryDtos;
	}

	public ItemDto convertEntityToDto(Item item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setItemId(item.getItemId());
		itemDto.setItemName(item.getItemName());
		return itemDto;
	}

}
