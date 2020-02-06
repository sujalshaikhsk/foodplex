package com.spiralforge.foodplex.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@SequenceGenerator(name = "orderSequence", initialValue = 100100)
public class OrderDetail implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
	private Integer orderDetailId;
	private Integer quantity;
	private Double totalPrice;
	private LocalDateTime orderDate;
	private String paymentMode;
	private String status;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "orderDetail")
	private List<OrderItem> orderItems;
}
