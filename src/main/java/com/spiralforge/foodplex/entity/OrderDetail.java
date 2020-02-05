package com.spiralforge.foodplex.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Setter
@Getter
@Entity
@Table(name = "order_detail")
@SequenceGenerator(name = "creditcardsequence", initialValue = 100100)
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderDetailId;
	private Integer quantity;
	private Double totalPrice;
	private LocalDateTime orderDate;
	private String status;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="vendor_item_id")
	private VendorItem vendorItem;
}
