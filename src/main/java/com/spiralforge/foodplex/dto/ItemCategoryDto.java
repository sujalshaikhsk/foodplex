package com.spiralforge.foodplex.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemCategoryDto {

	private String categoryName;
	private List<ItemDto> itemList;
}
