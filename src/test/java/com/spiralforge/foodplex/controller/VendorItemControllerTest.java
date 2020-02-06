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

import com.spiralforge.foodplex.dto.ResponseDto;
import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.service.VendorItemService;
import com.spiralforge.foodplex.util.ApiConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VendorItemControllerTest {

	@InjectMocks
	VendorItemController vendorItemController;
	
	@Mock
	VendorItemService vendorItemService;
	
	Integer vendorId = 10;	 
	VendorItemDto vendorItemDto = new VendorItemDto();
	ResponseDto resposeDto = new ResponseDto();
	List<VendorItemDto> vendorItemList = new ArrayList<VendorItemDto>();
	
	@Before
	public void setUp() {		
		vendorItemDto.setCategoryId(1);
		vendorItemDto.setCategoryName("Beverages");
		vendorItemDto.setItemId(100);
		vendorItemDto.setPrice(34);
		vendorItemList.add(vendorItemDto);
		
		resposeDto.setMessage(ApiConstant.SUCCESS);
		resposeDto.setStatusCode(ApiConstant.SUCCESS_CODE);			
	}

	@Test
	public void testSaveVendorItemDetails() throws VendorNotFoundException, UserNotFoundException {
		Mockito.when(vendorItemService.saveVendorItemDetails(vendorId, vendorItemDto)).thenReturn(resposeDto);
		resposeDto = vendorItemController.saveVendorItemDetails(vendorId, vendorItemDto).getBody();
		assertEquals(200, resposeDto.getStatusCode());
	}
	
	@Test
	public void testGetVendorItemDetails() {
		Mockito.when(vendorItemService.getVendorItemDetails()).thenReturn(vendorItemList);
		vendorItemList = vendorItemController.getVendorItemDetails().getBody();
		assertEquals(1, vendorItemList.size());
	}	
}
