package com.spiralforge.foodplex.service;

import static org.junit.Assert.assertNotNull;
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
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.Vendor;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.repository.ItemRepository;
import com.spiralforge.foodplex.repository.UserRepository;
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
	
	@Mock
	UserRepository userRepository;
	
	VendorItemDto vendorItemDto = new VendorItemDto();
	ResponseDto resposeDto = new ResponseDto();	
	List<Item> itemList = new ArrayList<Item>();	
	List<VendorItemDto> vendorItemDtoList = new ArrayList<VendorItemDto>();	
	VendorItem vendorItem = new VendorItem();
	User user = new User();
	
	@Before
	public void setUp() {	
		user.setUserId(1);
		
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
		
		user.setFirstName("TestFirstName");
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testSaveVendorItemDetailsUserNotFoundException() throws VendorNotFoundException, UserNotFoundException {
		vendorItemServiceImpl.saveVendorItemDetails(null, vendorItemDto);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testSaveVendorItemDetailsVendorNotFoundException1() throws VendorNotFoundException, UserNotFoundException {
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
		vendorItemServiceImpl.saveVendorItemDetails(1, vendorItemDto);
	}
	
	@Test(expected = VendorNotFoundException.class)
	public void testSaveVendorItemDetailsVendorNotFoundException() throws VendorNotFoundException, UserNotFoundException {
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		Mockito.when(vendorRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		vendorItemServiceImpl.saveVendorItemDetails(1, vendorItemDto);
	}
	
	@Test
	public void testSaveVendorItemDetails() throws VendorNotFoundException, UserNotFoundException {
		
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		Mockito.when(vendorRepository.findByUser(Mockito.any())).thenReturn(Optional.of(vendor));
		Mockito.when(itemRepository.getOne(Mockito.any())).thenReturn(new Item());
		Mockito.when(vendorItemRepository.save(Mockito.any())).thenReturn(new VendorItem());
		assertNotNull(vendorItemServiceImpl.saveVendorItemDetails(1, vendorItemDto));
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
