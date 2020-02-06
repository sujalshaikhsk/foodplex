package com.spiralforge.foodplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.Vendor;

/**
 * @author Sri Keerthna. 
 * @since 2020-02-05.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer>{	

}
