package com.spiralforge.foodplex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.repository.VendorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	VendorRepository vendorRepository;
	
	
}
