package com.spiralforge.foodplex.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Getter
@Setter
public class LoginResponseDto {

	private String message;
	private Integer statusCode;
	private String role;
	private Integer userId;
}
