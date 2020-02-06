package com.spiralforge.foodplex.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequestDto implements Serializable{
	
	String paymentMode;
	String upiId;
	List<OrderItemDto> orderList;
}
