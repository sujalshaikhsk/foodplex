package com.spiralforge.foodplex.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.spiralforge.foodplex.exception.InvalidUpiIdException;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.payment.PayTM;
import com.spiralforge.foodplex.payment.Payment;
import com.spiralforge.foodplex.payment.PaymentFactory;
import com.spiralforge.foodplex.payment.PhonePe;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.util.ApiConstant;
import com.spiralforge.foodplex.util.Constant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private OrderDetailService orderDetailService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PaymentFactory paymentFactory;


	User user = new User();
	List<User> userList = new ArrayList<>();
	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	List<UserResponseDto> vendorList = new ArrayList<>();
	UserResponseDto userResponseDto = new UserResponseDto();
	OrderRequestDto orderRequestDto = new OrderRequestDto();
	OrderItemDto orderItemDto = new OrderItemDto();
	List<OrderItemDto> orderList = new ArrayList<>();
	OrderResponseDto orderResponseDto = new OrderResponseDto();
	OrderDetail orderDetail = new OrderDetail();
	Vendor vendor = new Vendor();
	Item item = new Item();
	VendorItem vendorItem = new VendorItem();
	OrderItem orderItem = new OrderItem();
	List<OrderItem> orderItemList = new ArrayList<>();
	OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
	List<OrderDetail> orderDetailList = new ArrayList<>();

	@Before
	public void setUp() {
		user.setFirstName("Sri");
		user.setLastName("Keerthi");
		user.setMobileNumber("1234568797");
		user.setPassword("sri");
		user.setRole("USER");
		user.setUserId(1);
		user.setUpiId("sujal@upi");

		loginRequestDto.setMobileNumber("1234568797");
		loginRequestDto.setPassword("sri");
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
	public void testSaveOrderDetailPositive() throws InvalidUpiIdException {
		logger.info("Got the list of flights");
		Integer userId=1;
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Payment payment= new PayTM();
		Mockito.when(paymentFactory.getPaymentMethod("PayTM")).thenReturn(payment);
		Mockito.when(orderDetailService.saveOrderDetail(user, orderRequestDto)).thenReturn(orderDetail);
		
		OrderDetail result = userServiceImpl.placeOrder(userId, orderRequestDto);
		assertEquals(Integer.valueOf(1), result.getOrderDetailId());
	}
	
	@Test
	public void testSaveOrderDetailNegative() throws InvalidUpiIdException {
		logger.info("Got the list of flights");
		Integer userId=1;
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null));	
		OrderDetail result = userServiceImpl.placeOrder(userId, orderRequestDto);
		assertNull(result);
	}

	@Test(expected = InvalidUpiIdException.class)
	public void testSaveOrderDetailForException() throws InvalidUpiIdException {
		Integer userId=1;
		orderRequestDto.setUpiId("else");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Payment payment= new PhonePe();
		Mockito.when(paymentFactory.getPaymentMethod("PayTM")).thenReturn(payment);
		OrderDetail result = userServiceImpl.placeOrder(userId, orderRequestDto);
	}
	
	@Test
	public void testGetOrderPositive() {
		Integer userId=1;
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(orderDetailService.getAllOrdersByUser(user)).thenReturn(orderDetailList);
		
		List<OrderDetail> result = userServiceImpl.getOrders(userId);
		assertEquals(1, result.size());
	}
	
	@Test
	public void testGetOrderNegative() {
		Integer userId=1;
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null));
		
		List<OrderDetail> result = userServiceImpl.getOrders(userId);
		assertNull(result);
	}
	
	@Test
	public void testGetVendorOrderPositive() {
		Integer userId=1;
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(orderDetailService.getVendorOrders(user)).thenReturn(orderDetailList);
		
		List<OrderDetail> result = userServiceImpl.getVendorOrders(userId);
		assertEquals(1, result.size());
	}
	
	@Test
	public void testGetVendorOrderNegative() {
		Integer userId=1;
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null));
		
		List<OrderDetail> result = userServiceImpl.getVendorOrders(userId);
		assertNull(result);
	}


	@Test
	public void testUserLoginPositive() throws MobileNumberNotValidException, UserNotFoundException {
		logger.info("Logged in successfully");
		Mockito.when(userRepository.findByMobileNumberAndPassword(loginRequestDto.getMobileNumber(),
				loginRequestDto.getPassword())).thenReturn(Optional.of(user));
		loginResponseDto=userServiceImpl.userLogin(loginRequestDto);
		assertEquals(200, loginResponseDto.getStatusCode());
	}

	@Test(expected = MobileNumberNotValidException.class)
	public void testUserLoginNegative() throws MobileNumberNotValidException, UserNotFoundException {
		logger.error("Mobile number is not valid");
		loginRequestDto.setMobileNumber("12348797");
		userServiceImpl.userLogin(loginRequestDto);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testUserLoginNegativeException() throws MobileNumberNotValidException, UserNotFoundException {
		logger.error("Incorrect credentials");	
		Mockito.when(userRepository.findByMobileNumberAndPassword("5678908976",
				"chet")).thenReturn(Optional.of(user));
		userServiceImpl.userLogin(loginRequestDto);
	}
	
	@Test
	public void testVendorListPositive() throws VendorNotFoundException {
		Mockito.when(userRepository.findByRole(Constant.VENDOR)).thenReturn(userList);
		vendorList=userServiceImpl.vendorList();
		assertEquals(1, vendorList.size());
	}
	
	@Test(expected = VendorNotFoundException.class)
	public void testVendorListNegative() throws VendorNotFoundException {
		logger.error("vendors not found");
		List<User> userLists = new ArrayList<>();
		Mockito.when(userRepository.findByRole(Constant.VENDOR)).thenReturn(userLists);
		vendorList=userServiceImpl.vendorList();
	}
}
