package com.spiralforge.foodplex.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorItemDto {
	
	private double price;
	private int itemId;
	private String itemName;
	private int categoryId;
	private String categoryName;
	
}
