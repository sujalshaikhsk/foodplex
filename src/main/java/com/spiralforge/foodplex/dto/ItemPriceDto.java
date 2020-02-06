package com.spiralforge.foodplex.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ItemPriceDto {
	private Integer itemId;
	private String itemName;
	private Double itemPrice;
	private Integer categoryId; 
}
