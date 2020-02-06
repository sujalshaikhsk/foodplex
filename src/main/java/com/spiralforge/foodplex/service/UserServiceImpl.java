package com.spiralforge.foodplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.exception.InvalidOrderException;
import com.spiralforge.foodplex.exception.InvalidUpiIdException;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.payment.Payment;
import com.spiralforge.foodplex.payment.PaymentFactory;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.util.ApiConstant;
import com.spiralforge.foodplex.util.Constant;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentFactory paymentFactory;

	/**
	 * @author Sujal
	 *
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
	@Override
	public OrderDetail placeOrder(Integer userId, OrderRequestDto orderRequestDto) throws InvalidUpiIdException {
		OrderDetail orderDetail = null;
		Optional<User> user = getUserByUserId(userId);
		if (user.isPresent()) {
			Payment payment = paymentFactory.getPaymentMethod(orderRequestDto.getPaymentMode());
			if (payment.pay(orderRequestDto.getUpiId(), user.get())) {
				logger.info("upi is valid");
				orderDetail = orderDetailService.saveOrderDetail(user.get(), orderRequestDto);
			} else {
				logger.error("upi is not valid");
				throw new InvalidUpiIdException(ApiConstant.INVALID_UPI);
			}
		} else {
			logger.error("inside user not found");
		}
		return orderDetail;
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch the orders for the user.
	 *
	 * @param userId is used to fetch the user
	 * @return is the user.
	 */
	@Override
	public Optional<User> getUserByUserId(Integer userId) {
		return userRepository.findById(userId);
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch the orders for the user.
	 *
	 * @param userId is used to fetch the user
	 * @return is the list of detail of the orders.
	 */
	@Override
	public List<OrderDetail> getOrders(Integer userId) {
		List<OrderDetail> orderDetails = null;
		Optional<User> optionalUser = getUserByUserId(userId);
		if (optionalUser.isPresent()) {
			orderDetails = orderDetailService.getAllOrdersByUser(optionalUser.get());
		} else {
			logger.error("user is not present");
		}
		return orderDetails;
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch the orders for the vendor.
	 *
	 * @param userId is used to fetch the user
	 * @return is the list of detail of the orders.
	 */
	@Override
	public List<OrderDetail> getVendorOrders(Integer userId) {
		List<OrderDetail> orderDetails = null;
		Optional<User> optionalUser = getUserByUserId(userId);
		if (optionalUser.isPresent()) {
			orderDetails = orderDetailService.getVendorOrders(optionalUser.get());
		} else {
			logger.error("user is not present");
		}
		return orderDetails;
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
	@Override
	public LoginResponseDto userLogin(@Valid LoginRequestDto loginRequestDto)
			throws MobileNumberNotValidException, UserNotFoundException {
		if (loginRequestDto.getMobileNumber().length() != Constant.NUMBER_LENGTH) {
			logger.error("Mobile number is not valid");
			throw new MobileNumberNotValidException(ApiConstant.MOBILE_NUMBER_VALIDATION);
		} else {
			Optional<User> user = userRepository.findByMobileNumberAndPassword(loginRequestDto.getMobileNumber(),
					loginRequestDto.getPassword());
			if (!user.isPresent()) {
				logger.error("Incorrect credentials");
				throw new UserNotFoundException(ApiConstant.LOGIN_ERROR);
			} else {
				logger.info("Logged in successfully");
				LoginResponseDto loginResponseDto = new LoginResponseDto();
				BeanUtils.copyProperties(user.get(), loginResponseDto);
				loginResponseDto.setMessage(ApiConstant.LOGIN_SUCCESS);
				loginResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
				return loginResponseDto;
			}
		}
	}

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of vendors will be displayed.
	 * @return list of vendors are displayed.
	 * @throws VendorNotFoundException if vendors are not available
	 */
	@Override
	public List<UserResponseDto> vendorList() throws VendorNotFoundException {
		List<UserResponseDto> userResponseDto = new ArrayList<>();
		List<User> userList = userRepository.findByRole(Constant.VENDOR);
		if (userList.isEmpty()) {
			logger.error("vendors not found");
			throw new VendorNotFoundException(ApiConstant.VENDOR_NOT_FOUND_EXCEPTION);
		} else {
			userList.forEach(list -> {
				UserResponseDto responseDto = new UserResponseDto();
				BeanUtils.copyProperties(list, responseDto);
				userResponseDto.add(responseDto);
			});
			logger.info("got the list of vendors");
			return userResponseDto;
		}
	}

}
