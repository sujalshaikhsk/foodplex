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
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.util.ApiConstant;
import com.spiralforge.foodplex.util.Constant;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

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
