package com.spiralforge.foodplex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.foodplex.entity.User;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. It will validate the mobile number and password in
	 *        database.
	 * @param mobileNumber given by user.
	 * @param password     given by user.
	 * @return details of that particular user.
	 */
	Optional<User> findByMobileNumberAndPassword(String mobileNumber, String password);

}
