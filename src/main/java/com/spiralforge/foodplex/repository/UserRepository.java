package com.spiralforge.foodplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.Vendor;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}
