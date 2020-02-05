package com.spiralforge.foodplex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	User user = new User();
	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	List<UserResponseDto> vendorList = new ArrayList<>();
	UserResponseDto userResponseDto = new UserResponseDto();
	
	@Before
	public void setUp() {
		user.setFirstName("Sri");
		user.setLastName("Keerthi");
		user.setMobileNumber("1234568797");
		user.setPassword("sri");
		user.setRole("USER");
		user.setUserId(1);
		
		loginRequestDto.setMobileNumber("1234568797");
		loginRequestDto.setPassword("sri");
		
		BeanUtils.copyProperties(user, loginResponseDto);
		
		userResponseDto.setFirstName("sri");
		userResponseDto.setLastName("keerthi");
		userResponseDto.setUserId(1);
		vendorList.add(userResponseDto);
	}

	@Test
	public void testUserLoginPositive() throws MobileNumberNotValidException, UserNotFoundException {
		logger.info("Entered into userLogin method in controller");
		Mockito.when(userService.userLogin(loginRequestDto)).thenReturn(loginResponseDto);
		Integer result=userController.userLogin(loginRequestDto).getStatusCodeValue();
		assertEquals(200, result);
	}
	
	@Test
	public void testVendorListPositive() throws VendorNotFoundException {
		logger.info("Entered into vendorList method in controller");
		Mockito.when(userService.vendorList()).thenReturn(vendorList);
		Integer result=userController.vendorList().getStatusCodeValue();
		assertEquals(200, result);
	}
}
