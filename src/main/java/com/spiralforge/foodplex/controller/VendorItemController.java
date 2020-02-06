package com.spiralforge.foodplex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.foodplex.dto.ResponseDto;
import com.spiralforge.foodplex.dto.VendorItemDto;
import com.spiralforge.foodplex.exception.UserNotFoundException;
import com.spiralforge.foodplex.exception.VendorNotFoundException;
import com.spiralforge.foodplex.service.VendorItemService;

/**
 * Item Controller to handle CRUD operations.
 */
@RestController
@RequestMapping("/vendors")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class VendorItemController {

	@Autowired
	VendorItemService vendorItemService;

	/**
	 * @author Raghavendra A. This method is used to save vendor item details.
	 * @return Response message and code.
	 * @throws VendorNotFoundException if vendors are not available
	 */
	@PostMapping(path = "/{vendorId}/item")
	public ResponseEntity<ResponseDto> saveVendorItemDetails(@PathVariable("vendorId") Integer vendorId,
			@RequestBody VendorItemDto vendorItemDto) throws VendorNotFoundException, UserNotFoundException {
		ResponseDto responseDto = vendorItemService.saveVendorItemDetails(vendorId, vendorItemDto);
		return ResponseEntity.ok().body(responseDto);
	}

	/**
	 * @author Raghavendra A. This method is used to get all items, so that vendor
	 *         can pick and choose item to build his list of items.
	 * @return Returns list of items.
	 */
	@GetMapping
	public ResponseEntity<List<VendorItemDto>> getVendorItemDetails() {
		List<VendorItemDto> vendorItemDto = vendorItemService.getVendorItemDetails();
		return ResponseEntity.ok().body(vendorItemDto);
	}
}
