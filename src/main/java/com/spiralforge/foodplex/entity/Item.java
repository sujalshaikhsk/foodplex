package com.spiralforge.foodplex.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "item")
public class Item implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;
	private String itemName;

	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;
}
