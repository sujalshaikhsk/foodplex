package com.spiralforge.foodplex.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.OrderRequestDto;
import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.Vendor;
import com.spiralforge.foodplex.exception.InvalidOrderException;
import com.spiralforge.foodplex.exception.InvalidUpiIdException;
import com.spiralforge.foodplex.payment.Payment;
import com.spiralforge.foodplex.payment.PaymentFactory;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.repository.VendorRepository;
import com.spiralforge.foodplex.util.ApiConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VendorRepository vendorRepository;

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

}
