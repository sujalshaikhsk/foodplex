package com.spiralforge.foodplex.service;

import static org.junit.Assert.assertEquals;

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
import com.spiralforge.foodplex.repository.OrderItemRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderItemServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceTest.class);

	@InjectMocks
	private OrderItemServiceImpl orderItemServiceImpl;

	@Mock
	private VendorItemService vendorItemService;

	@Mock
	private OrderItemRepository orderItemRepository;

	User user = new User();
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
	public void testSaveOrderItemsPositive() {
		logger.info("Got the list of flights");
		Integer vendorItemId = 1;
		List<OrderItem> ordItmList = new ArrayList<>();
		Mockito.when(vendorItemService.getVendorItemById(vendorItemId)).thenReturn(Optional.of(vendorItem));
		Mockito.when(orderItemRepository.saveAll(orderItemList)).thenReturn(orderItemList);
		ordItmList = orderItemServiceImpl.saveOrderItems(orderDetail, orderList);
		assertEquals(1, orderItemList.size());
	}

}
