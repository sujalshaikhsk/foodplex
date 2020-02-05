package com.spiralforge.foodplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}
