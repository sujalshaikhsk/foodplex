package com.spiralforge.foodplex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.service.VendorItemService;

/**
 * Item Controller to handle CRUD operations.
 */
@RestController
@RequestMapping("/vendors")
public class VendorItemController {
	
	@Autowired
	VendorItemService vendorItemService;
			
	@PostMapping(path = "/{vendorId}/item", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> saveVendorItemDetails(@PathVariable("vendorId") Integer vendorId, @RequestBody VendorItemDto vendorItemDto) {	
		vendorItemService.saveVendorItemDetails(vendorId, vendorItemDto);		
		return ResponseEntity.ok().body("Item saved successfully");
	}
	
	@GetMapping
	public ResponseEntity<List<VendorItemDto>> getVendorItemDetails() {	
		List<VendorItemDto> vendorItemDto = vendorItemService.getVendorItemDetails();		
		return ResponseEntity.ok().body(vendorItemDto);
	}
}
