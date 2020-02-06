package com.spiralforge.foodplex.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.ItemCategoryDto;
import com.spiralforge.foodplex.dto.ItemDto;
import com.spiralforge.foodplex.dto.ItemPriceDto;
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
		User user = userRepository.findByUserId(userId);
		List<VendorItem> vendorItemList = vendorItemRepository.findVendorItemByUser(user);

		List<ItemPriceDto> itemList = vendorItemList.stream().map(item -> convertVendorItemEntityToDto(item))
				.collect(Collectors.toList());

		List<Item> items = itemRepository.findAll();
		Set<Category> nameSet = new HashSet<>();
		List<Item> categoryList = items.stream().filter(e -> nameSet.add(e.getCategory())).collect(Collectors.toList());

		List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
		categoryList.forEach(category -> {
			ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
			itemCategoryDto.setCategoryName(category.getCategory().getCategoryName());

			List<ItemDto> itemDtos = itemList.stream()
					.filter(itemPriceDto -> category.getCategory().getCategoryId().equals(itemPriceDto.getCategoryId()))
					.map(itemPriceDto -> convertEntityToDto(itemPriceDto)).collect(Collectors.toList());
			itemCategoryDto.setItemList(itemDtos);
			itemCategoryDtos.add(itemCategoryDto);

		});
		logger.info("got the list of categories");
		return itemCategoryDtos;
	}

	public ItemPriceDto convertVendorItemEntityToDto(VendorItem item) {
		ItemPriceDto itemDto = new ItemPriceDto();
		itemDto.setItemId(item.getItem().getItemId());
		itemDto.setItemName(item.getItem().getItemName());
		itemDto.setItemPrice(item.getPrice());
		itemDto.setCategoryId(item.getItem().getCategory().getCategoryId());
		return itemDto;
	}

	public ItemDto convertEntityToDto(ItemPriceDto item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setItemId(item.getItemId());
		itemDto.setItemName(item.getItemName());
		itemDto.setItemPrice(item.getItemPrice());
		return itemDto;
	}

}
