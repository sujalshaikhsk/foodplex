package com.spiralforge.foodplex.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.OrderDetailsDto;
import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.dto.OrderResponseDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.exception.InvalidOrderException;
import com.spiralforge.foodplex.exception.InvalidUpiIdException;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.service.UserService;
import com.spiralforge.foodplex.util.ApiConstant;
import com.spiralforge.foodplex.util.OrderValidator;

/**
 * This controller is having login functionality.
 * 
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private OrderValidator<Integer, OrderRequestDto> orderValidator;

	/**
	 * @author Sujal 
	 *         This method is used to place the order to the vendor. After the
	 *         payment done ,save all the items for the order and calculate the
	 *         total price. 
	 *         
	 * @param userId          is used to fetch the user
	 * @param orderRequestDto is the item details
	 * @return OrderResponseDto is the detail of the orders.
	 * @throws InvalidOrderException
	 * @throws InvalidUpiIdException
	 */
	@PostMapping("{userId}/order")
	public ResponseEntity<OrderResponseDto> placeOrder(@PathVariable("userId") Integer userId,
			@RequestBody OrderRequestDto orderRequestDto) throws InvalidOrderException, InvalidUpiIdException {

		if (orderValidator.validate(userId, orderRequestDto)) {
			OrderResponseDto orderResponseDto = new OrderResponseDto();
			OrderDetail orderDetail = userService.placeOrder(userId, orderRequestDto);
			logger.info("place order started");
			if (Objects.isNull(orderDetail)) {
				orderResponseDto.setStatusCode(ApiConstant.NO_CONTENT_CODE);
				orderResponseDto.setMessage(ApiConstant.NO_ELEMENT_FOUND);
				return new ResponseEntity<>(orderResponseDto, HttpStatus.NO_CONTENT);
			} else {
				BeanUtils.copyProperties(orderDetail, orderResponseDto);
				orderResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
				orderResponseDto.setMessage(ApiConstant.SUCCESS);
				return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);

			}
		} else {
			logger.error("invalid order data");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch the orders for the user.
	 *
	 * @param userId is used to fetch the user
	 * @return is the list of detail of the orders.
	 */
	@GetMapping("{userId}/orders")
	public ResponseEntity<OrderDetailsDto> getOrders(@PathVariable("userId") Integer userId) {

		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
		List<OrderDetail> orders = userService.getOrders(userId);
		logger.info("inside get orders ");
		if (Objects.isNull(orders) || orders.isEmpty()) {
			orderDetailsDto.setStatusCode(ApiConstant.NO_CONTENT_CODE);
			orderDetailsDto.setMessage(ApiConstant.NO_ELEMENT_FOUND);
			return new ResponseEntity<>(orderDetailsDto, HttpStatus.NO_CONTENT);
		} else {
			orderDetailsDto.setOrders(orders);
			orderDetailsDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			orderDetailsDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(orderDetailsDto, HttpStatus.OK);
		}
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to filter and sort the flight result based on flight
	 *         name and price respectively. This filter and sorting will be
	 *         performed on the top of existing search.
	 *
	 * @param userId is the userId of the vendor
	 * @return OrderResponseDto is the list of detail of the orders.
	 */
	@GetMapping("{userId}/vendororders")
	public ResponseEntity<OrderDetailsDto> getVendorOrders(@PathVariable("userId") Integer userId) {

		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
		List<OrderDetail> orders = userService.getVendorOrders(userId);
		logger.info("get orders for vendors");
		if (Objects.isNull(orders) || orders.isEmpty()) {
			orderDetailsDto.setStatusCode(ApiConstant.NO_CONTENT_CODE);
			orderDetailsDto.setMessage(ApiConstant.NO_ELEMENT_FOUND);
			return new ResponseEntity<>(orderDetailsDto, HttpStatus.NO_CONTENT);
		} else {
			orderDetailsDto.setOrders(orders);
			orderDetailsDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			orderDetailsDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(orderDetailsDto, HttpStatus.OK);
		}
	}

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method mobile number and password is taken from
	 *        user for login.
	 * @param longinRequestDto contains mobile number and password.
	 * @return message with status code and role of that particular mobile number.
	 * @throws MobileNumberNotValidException length of mobile number length is
	 *                                       validated.
	 * @throws UserNotFoundException         if incorrect credentials are given it
	 *                                       will throw this exception.
	 */
	@PostMapping
	public ResponseEntity<LoginResponseDto> userLogin(@Valid @RequestBody LoginRequestDto loginRequestDto)
			throws MobileNumberNotValidException, UserNotFoundException {
		logger.info("Entered into userLogin method in controller");
		return new ResponseEntity<>(userService.userLogin(loginRequestDto), HttpStatus.OK);
	}

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of vendors will be displayed.
	 * @return list of vendors are displayed.
	 * @throws VendorNotFoundException if vendors are not available 
	 */
	@GetMapping
	public ResponseEntity<List<UserResponseDto>> vendorList() throws VendorNotFoundException {
		logger.info("Entered into vendorList method in controller");
		return new ResponseEntity<>(userService.vendorList(), HttpStatus.OK);

	}

}
