package com.spiralforge.foodplex.service;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
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
	public LoginResponseDto userLogin(@Valid LoginRequestDto longinRequestDto)
			throws MobileNumberNotValidException, UserNotFoundException {
		if (longinRequestDto.getMobileNumber().length() != Constant.NUMBER_LENGTH) {
			logger.error("Mobile number is not valid");
			throw new MobileNumberNotValidException(ApiConstant.MOBILE_NUMBER_VALIDATION);
		} else {
			Optional<User> user = userRepository.findByMobileNumberAndPassword(longinRequestDto.getMobileNumber(),
					longinRequestDto.getPassword());
			if (!user.isPresent()) {
				logger.error("Incorrect credentials");
				throw new UserNotFoundException(ApiConstant.LOGIN_ERROR);
			} else {
				logger.info("Logged in successfully");
				LoginResponseDto loginResponseDto = new LoginResponseDto();
				loginResponseDto.setRole(user.get().getRole());
				loginResponseDto.setMessage(ApiConstant.LOGIN_SUCCESS);
				loginResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
				return loginResponseDto;
			}
		}
	}

}
