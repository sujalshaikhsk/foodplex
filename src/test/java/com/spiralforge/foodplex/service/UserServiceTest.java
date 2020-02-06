package com.spiralforge.foodplex.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.service.UserServiceImpl;
import com.spiralforge.foodplex.util.ApiConstant;
import com.spiralforge.foodplex.util.Constant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	User user = new User();
	List<User> userList = new ArrayList<>();
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
		user.setRole("VENDOR");
		user.setUserId(1);
		userList.add(user);
		
		BeanUtils.copyProperties(userList, userResponseDto);
		vendorList.add(userResponseDto);
		
		loginRequestDto.setMobileNumber("1234568797");
		loginRequestDto.setPassword("sri");

		loginResponseDto.setMessage(ApiConstant.LOGIN_SUCCESS);
		loginResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);

		BeanUtils.copyProperties(user, loginResponseDto);

		userResponseDto.setFirstName("sri");
		userResponseDto.setLastName("keerthi");
		userResponseDto.setUserId(1);
		vendorList.add(userResponseDto);
	}

	@Test
	public void testUserLoginPositive() throws MobileNumberNotValidException, UserNotFoundException {
		logger.info("Logged in successfully");
		Mockito.when(userRepository.findByMobileNumberAndPassword(loginRequestDto.getMobileNumber(),
				loginRequestDto.getPassword())).thenReturn(Optional.of(user));
		loginResponseDto=userService.userLogin(loginRequestDto);
		assertEquals(200, loginResponseDto.getStatusCode());
	}

	@Test(expected = MobileNumberNotValidException.class)
	public void testUserLoginNegative() throws MobileNumberNotValidException, UserNotFoundException {
		logger.error("Mobile number is not valid");
		loginRequestDto.setMobileNumber("12348797");
		userService.userLogin(loginRequestDto);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testUserLoginNegativeException() throws MobileNumberNotValidException, UserNotFoundException {
		logger.error("Incorrect credentials");	
		Mockito.when(userRepository.findByMobileNumberAndPassword("5678908976",
				"chet")).thenReturn(Optional.of(user));
		userService.userLogin(loginRequestDto);
	}
	
	@Test
	public void testVendorListPositive() throws VendorNotFoundException {
		Mockito.when(userRepository.findByRole(Constant.VENDOR)).thenReturn(userList);
		vendorList=userService.vendorList();
		assertEquals(1, vendorList.size());
	}
	
	@Test(expected = VendorNotFoundException.class)
	public void testVendorListNegative() throws VendorNotFoundException {
		logger.error("vendors not found");
		List<User> userLists = new ArrayList<>();
		Mockito.when(userRepository.findByRole(Constant.VENDOR)).thenReturn(userLists);
		vendorList=userService.vendorList();
	}
}
