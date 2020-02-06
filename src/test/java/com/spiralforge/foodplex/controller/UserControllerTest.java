package com.spiralforge.foodplex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.OrderDetailsDto;
import com.spiralforge.foodplex.dto.OrderItemDto;
import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.dto.OrderResponseDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.entity.Item;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.OrderItem;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.Vendor;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.exception.InvalidOrderException;
import com.spiralforge.foodplex.exception.InvalidUpiIdException;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.service.UserService;
import com.spiralforge.foodplex.service.VendorItemService;
import com.spiralforge.foodplex.util.ApiConstant;
import com.spiralforge.foodplex.util.OrderValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;
	
	@Mock
	private VendorItemService vendorItemService;
	
	@Mock
	OrderValidator<Integer, OrderRequestDto> orderValidator;

	User user = new User();
	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	List<UserResponseDto> vendorList = new ArrayList<>();
	UserResponseDto userResponseDto = new UserResponseDto();
	OrderRequestDto orderRequestDto = new OrderRequestDto();
	OrderItemDto orderItemDto = new OrderItemDto();
	List<OrderItemDto> orderList=new ArrayList<>();
	OrderResponseDto orderResponseDto = new OrderResponseDto();
	OrderDetail orderDetail = new OrderDetail();
	Vendor vendor= new Vendor();
	Item item=new Item();
	VendorItem vendorItem=new VendorItem();
	OrderItem orderItem= new OrderItem();
	List<OrderItem> orderItemList=new ArrayList<>();
	OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
	List<OrderDetail> orderDetailList=new ArrayList<>();

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
		
		orderItemDto.setPrice(20D);
		orderItemDto.setQuantity(3);
		orderItemDto.setVendorItemId(1);
		orderList.add(orderItemDto);
		
		vendor.setVendorId(1);
		vendor.setUser(user);
		vendor.setVendorName("vendor");
		
		item.setItemId(1);
		item.setItemName("item");
		
		vendorItem.setVendorItemId(1);
		vendorItem.setPrice(20D);
		vendorItem.setVendor(vendor);
		vendorItem.setItem(item);
				
		orderDetail.setPaymentMode("PayTM");
		orderDetail.setOrderDetailId(1);
		orderDetail.setOrderDate(LocalDateTime.now());
		orderDetail.setQuantity(3);
		orderDetail.setTotalPrice(60D);
		orderDetail.setPaymentMode("PayTM");
		
		orderItem.setOrderDetail(orderDetail);
		orderItem.setOrderItemId(1);
		orderItem.setPrice(20D);
		orderItem.setQuantity(3);
		orderItemList.add(orderItem);
		orderDetail.setOrderItems(orderItemList);

		
		orderRequestDto.setPaymentMode("PayTM");
		orderRequestDto.setUpiId("sujal@upi");
		orderRequestDto.setOrderList(orderList);
		
		orderResponseDto.setOrderDetailId(1);
		orderResponseDto.setOrderDate(LocalDateTime.now());
		orderResponseDto.setQuantity(3);
		orderResponseDto.setTotalPrice(60D);
		orderResponseDto.setPaymentMode("PayTM");
		
		orderDetailList.add(orderDetail);
		orderDetailsDto.setOrders(orderDetailList);
		
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
	
	@Test
	public void testPlaceOrderPositive() throws InvalidUpiIdException, InvalidOrderException {
		logger.info("Entered into vendorList method in controller");
		Integer userId=1;
		Mockito.when(orderValidator.validate(userId, orderRequestDto)).thenReturn(true);
		Mockito.when(vendorItemService.getVendorItemById(2)).thenReturn(Optional.of(vendorItem));
		Mockito.when(userService.placeOrder(userId, orderRequestDto)).thenReturn(orderDetail);
		ResponseEntity<OrderResponseDto> response =userController.placeOrder(userId, orderRequestDto);
		assertEquals(ApiConstant.SUCCESS_CODE, response.getBody().getStatusCode());
	}
	
	@Test
	public void testPlaceOrderForNegative() throws InvalidUpiIdException, InvalidOrderException {
		logger.info("Entered into vendorList method in controller");
		Integer userId=1;
		Mockito.when(orderValidator.validate(userId, orderRequestDto)).thenReturn(false);
		
		ResponseEntity<OrderResponseDto> response =userController.placeOrder(userId, orderRequestDto);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void testPlaceOrderNegative() throws InvalidUpiIdException {
		orderRequestDto.setUpiId(null);
		Mockito.when(vendorItemService.getVendorItemById(2)).thenReturn(Optional.of(vendorItem));
		Boolean result=vendorItemService.getVendorItemById(vendorItem.getVendorItemId()).isPresent();
		assertEquals(false, result);

	}
	
	@Test
	public void testPlaceOrderNeative() throws InvalidUpiIdException, InvalidOrderException {
		logger.info("Entered into vendorList method in controller");
		Integer userId=1;
		Mockito.when(orderValidator.validate(userId, orderRequestDto)).thenReturn(true);
		Mockito.when(vendorItemService.getVendorItemById(2)).thenReturn(Optional.of(vendorItem));
		Mockito.when(userService.placeOrder(userId, orderRequestDto)).thenReturn(null);
		ResponseEntity<OrderResponseDto> response =userController.placeOrder(userId, orderRequestDto);
		assertEquals(ApiConstant.NO_CONTENT_CODE, response.getBody().getStatusCode());
	}
	
	@Test
	public void testGetOrdersPositive(){
		Integer userId=1;
		Mockito.when(userService.getOrders(userId)).thenReturn(orderDetailList);
		ResponseEntity<OrderDetailsDto> response=userController.getOrders(userId);
		assertEquals(ApiConstant.SUCCESS_CODE, response.getBody().getStatusCode());

	}
	
	@Test
	public void testGetOrdersNegative(){
		Integer userId=1;
		Mockito.when(userService.getOrders(userId)).thenReturn(null);
		ResponseEntity<OrderDetailsDto> response=userController.getOrders(userId);
		assertEquals(ApiConstant.NO_CONTENT_CODE, response.getBody().getStatusCode());

	}
	
	
	@Test
	public void testGetVendorOrdersPositive(){
		Integer userId=1;
		Mockito.when(userService.getVendorOrders(userId)).thenReturn(orderDetailList);
		ResponseEntity<OrderDetailsDto> response=userController.getVendorOrders(userId);
		assertEquals(ApiConstant.SUCCESS_CODE, response.getBody().getStatusCode());

	}
	
	@Test
	public void testGetVendorOrdersNegative(){
		Integer userId=1;
		Mockito.when(userService.getVendorOrders(userId)).thenReturn(null);
		ResponseEntity<OrderDetailsDto> response=userController.getVendorOrders(userId);
		assertEquals(ApiConstant.NO_CONTENT_CODE, response.getBody().getStatusCode());

	}
	
	@Test
	public void testVendorList() throws VendorNotFoundException {
		logger.info("Entered into vendorList method in controller");
		Mockito.when(userService.vendorList()).thenReturn(vendorList);
	}
}
