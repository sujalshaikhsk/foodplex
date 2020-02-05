package com.spiralforge.foodplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.OrderDetail;
import com.spiralforge.foodplex.entity.User;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch all the orders.
	 * 
	 * @param user
	 * @return list of OrderDetail
	 */
	List<OrderDetail> findAllByUser(User user);

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch all the orders for vendor.
	 * 
	 * @param user
	 * @return list of OrderDetail
	 */
	@Query("select o from OrderDetail o inner join o.orderItems v where v.vendorItem.vendor.user=:user")
	List<OrderDetail> getVendorOrders(@Param("user") User user);
	
}
