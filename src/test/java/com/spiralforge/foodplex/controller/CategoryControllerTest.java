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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spiralforge.foodplex.dto.CategoryResponseDto;
import com.spiralforge.foodplex.dto.ItemCategoryDto;
import com.spiralforge.foodplex.dto.ItemDto;
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
	CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
	ItemCategoryDto itemCategoryDto= new ItemCategoryDto();
	List<ItemCategoryDto> itemCategoryDtoList = new ArrayList<>();
	List<ItemDto> itemList = new ArrayList<>();
	ItemDto ItemDto=new ItemDto();
	
	@Before
	public void setUp() {
		category.setCategoryId(1);
		category.setCategoryName("Salads");
		categoryList.add(category);
		
		categoryResponseDto.setStatusCode(HttpStatus.OK.value());
		
		ItemDto.setItemId(1);
		ItemDto.setItemName("tea");
		itemList.add(ItemDto);
		
		itemCategoryDto.setItemList(itemList);
		itemCategoryDto.setCategoryName("Salads");
		itemCategoryDtoList.add(itemCategoryDto);
		
	}
	
	@Test
	public void testCategoryListPositive() {
		logger.info("Entered into categoryList method in controller");
		Mockito.when(categoryService.getItemCategoryListByVendorId(1)).thenReturn(itemCategoryDtoList);
		ResponseEntity<CategoryResponseDto> result=categoryController.getItemCategoryListByVendorId(1);
		assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
	}
}
