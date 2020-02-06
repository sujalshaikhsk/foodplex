package com.spiralforge.foodplex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spiralforge.foodplex.entity.Category;
import com.spiralforge.foodplex.service.CategoryService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CategoryControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryControllerTest.class);

	@InjectMocks
	CategoryController categoryController;

	@Mock
	CategoryService categoryService;

	Category category = new Category();
	List<Category> categoryList = new ArrayList<>();

	@Before
	public void setUp() {
		category.setCategoryId(1);
		category.setCategoryName("Salads");
		categoryList.add(category);
	}
	
//	@Test
//	public void testCategoryListPositive() {
//		logger.info("Entered into categoryList method in controller");
//		Mockito.when(categoryService.categoryList()).thenReturn(categoryList);
//		Integer result=categoryController.categoryList().getStatusCodeValue();
//		assertEquals(200, result);
//	}
}
