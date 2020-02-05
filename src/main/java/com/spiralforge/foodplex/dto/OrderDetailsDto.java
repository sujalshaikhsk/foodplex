package com.spiralforge.foodplex.dto;

import java.io.Serializable;
import java.util.List;

import com.spiralforge.foodplex.entity.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailsDto implements Serializable{
	private List<OrderDetail> orders;
	private Integer statusCode;
	private String message;
}
