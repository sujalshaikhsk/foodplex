package com.spiralforge.foodplex.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.foodplex.dto.LoginRequestDto;
import com.spiralforge.foodplex.dto.LoginResponseDto;
import com.spiralforge.foodplex.dto.UserResponseDto;
import com.spiralforge.foodplex.exception.MobileNumberNotValidException;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.service.UserService;

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

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

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
