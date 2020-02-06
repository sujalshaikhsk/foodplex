package com.spiralforge.foodplex.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.spiralforge.foodplex.dto.ResponseDto;
import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.entity.Category;
import com.spiralforge.foodplex.entity.Item;
import com.spiralforge.foodplex.entity.Vendor;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.repository.ItemRepository;
import com.spiralforge.foodplex.repository.VendorItemRepository;
import com.spiralforge.foodplex.repository.VendorRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VendorItemServiceImplTest {
	
	@InjectMocks
	VendorItemServiceImpl vendorItemServiceImpl;
	
	@Mock
	VendorRepository vendorRepository;
	
	@Mock
	VendorItemRepository vendorItemRepository;
	
	@Mock
	ItemRepository itemRepository;
	
	VendorItemDto vendorItemDto = new VendorItemDto();
	ResponseDto resposeDto = new ResponseDto();	
	List<Item> itemList = new ArrayList<Item>();	
	List<VendorItemDto> vendorItemDtoList = new ArrayList<VendorItemDto>();	
	VendorItem vendorItem = new VendorItem();
	
	@Before
	public void setUp() {	
		vendorItemDto.setCategoryId(1);
		vendorItemDto.setCategoryName("Beverages");
		vendorItemDto.setItemId(100);
		vendorItemDto.setPrice(34);
				
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("TestCat");
		
		Item itemOne = new Item();
		itemOne.setItemId(1);
		itemOne.setItemName("Item1");
		itemOne.setCategory(category);
		
		Item itemTwo = new Item();
		itemTwo.setItemId(2);
		itemTwo.setItemName("Item2");
		itemTwo.setCategory(category);
		
		itemList.add(itemOne);
		itemList.add(itemTwo);
		
		vendorItem.setItem(new Item());
		vendorItem.setPrice(100.00);
		vendorItem.setVendor(new Vendor());
		vendorItem.setVendorItemId(1000);
	}
	
	@Test(expected = VendorNotFoundException.class)
	public void testSaveVendorItemDetailsWithNullVendorId() throws VendorNotFoundException {
		resposeDto = vendorItemServiceImpl.saveVendorItemDetails(null, vendorItemDto);
		assertEquals(200, resposeDto.getStatusCode());
	}
	
	@Test
	public void testSaveVendorItemDetails() throws VendorNotFoundException {
		resposeDto = vendorItemServiceImpl.saveVendorItemDetails(10, vendorItemDto);
		assertEquals(200, resposeDto.getStatusCode());
	}
	
	@Test
	public void testGetVendorItemDetails() {
		Mockito.when(itemRepository.findAll()).thenReturn(itemList);
		vendorItemDtoList = vendorItemServiceImpl.getVendorItemDetails();
		assertEquals(2, vendorItemDtoList.size());
	}
	
	@Test
	public void testGetVendorByItemId() {
		Mockito.when(vendorItemRepository.findById(10)).thenReturn(Optional.of(vendorItem));
		vendorItem = vendorItemServiceImpl.getVendorItemById(10).get();
		assertEquals(100, vendorItem.getPrice());
	}
}
