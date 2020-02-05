package com.spiralforge.foodplex.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.foodplex.entity.Category;
import com.spiralforge.foodplex.repository.CategoryRepository;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of categories are fetched from
	 *        database.
	 * @return List of categories are fetched.
	 */
	@Override
	public List<Category> categoryList() {
		logger.info("got the list of categories");
		return categoryRepository.findAll();
	}

}
