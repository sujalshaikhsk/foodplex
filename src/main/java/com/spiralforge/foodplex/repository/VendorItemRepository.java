package com.spiralforge.foodplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.VendorItem;

@Repository
public interface VendorItemRepository extends JpaRepository<VendorItem, Integer> {

	List<VendorItem> findVendorItemByUser(User user);

}
