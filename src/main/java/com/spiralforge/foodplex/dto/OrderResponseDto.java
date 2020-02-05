package com.spiralforge.foodplex.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponseDto implements Serializable{

	private Integer orderDetailId;
	private Integer quantity;
	private Double totalPrice;
	private LocalDateTime orderDate;
	private String paymentMode;
	private String status;
	private Integer statusCode;
	private String message;
}
