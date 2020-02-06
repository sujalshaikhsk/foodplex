package com.spiralforge.foodplex.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.foodplex.dto.CategoryResponseDto;
import com.spiralforge.foodplex.dto.ItemCategoryDto;
import com.spiralforge.foodplex.service.CategoryService;

/**
 * This controller is having listing category functionality.
 * 
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@RestController
@RequestMapping("/categories")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class CategoryController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of categories are fetched from
	 *        database.
	 * @return List of categories are fetched.
	 */
	@GetMapping
	public ResponseEntity<CategoryResponseDto> getItemCategoryListByVendorId(@RequestParam Integer userId) {
		logger.info("Entered into categoryList method in controller");
		List<ItemCategoryDto> itemCategoryList = categoryService.getItemCategoryListByVendorId(userId);
		CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
		categoryResponseDto.setStatusCode(HttpStatus.OK.value());
		categoryResponseDto.setItemCategoryList(itemCategoryList);
		return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);

	}

}
