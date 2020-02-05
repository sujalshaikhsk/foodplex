package com.spiralforge.foodplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.VendorItem;

@Repository
public interface VendorItemRepository extends JpaRepository<VendorItem, Long>{
	
}
