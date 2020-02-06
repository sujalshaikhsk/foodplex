package com.spiralforge.foodplex.service;

import java.util.List;

import com.spiralforge.foodplex.dto.ItemCategoryDto;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
public interface CategoryService {

	/**
	 * This method to get list of categories.
	 */
	public List<ItemCategoryDto> getItemCategoryListByVendorId(Integer userId);

}
