package com.spiralforge.foodplex.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {

	private String message;
	private Integer statusCode;
	private List<ItemCategoryDto> itemCategoryList;

}
