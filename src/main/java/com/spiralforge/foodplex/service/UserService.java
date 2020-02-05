package com.spiralforge.foodplex.service;

import java.util.List;

import javax.validation.Valid;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
public interface UserService {

	LoginResponseDto userLogin(@Valid LoginRequestDto longinRequestDto) throws MobileNumberNotValidException, UserNotFoundException;

	List<UserResponseDto> vendorList() throws VendorNotFoundException;

}
