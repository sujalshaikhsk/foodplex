package com.spiralforge.foodplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.VendorItem;

@Repository
public interface VendorItemRepository extends JpaRepository<VendorItem, Integer> {

	@Query("select v from VendorItem v inner join v.vendor vd where vd.user=:user")
	List<VendorItem> findVendorItemByUser(@Param("user") User user);

}
