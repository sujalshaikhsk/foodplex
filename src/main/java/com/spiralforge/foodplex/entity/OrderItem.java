package com.spiralforge.foodplex.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderItemId;
	private Integer quantity;
	private Double price;
	
	@OneToOne
	@JoinColumn(name="vendor_item_id")
	private VendorItem vendorItem;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="order_detail_id")
	private OrderDetail orderDetail;
}
