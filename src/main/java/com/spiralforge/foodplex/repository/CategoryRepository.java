package com.spiralforge.foodplex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
