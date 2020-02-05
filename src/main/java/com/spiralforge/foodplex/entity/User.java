package com.spiralforge.foodplex.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String firstName;
	private String lastName;
	private String password;
	private String role;
	private Long mobileNumber;
	private String upiId;
}
