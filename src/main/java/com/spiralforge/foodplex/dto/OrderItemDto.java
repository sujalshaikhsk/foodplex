package com.spiralforge.foodplex.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDto implements Serializable {

	private Integer vendorItemId;
	private Integer quantity;
	private Double price;
}
