package com.spiralforge.foodplex.payment;

import com.spiralforge.foodplex.entity.User;

public interface Payment {
	
    Boolean pay(String upiId, User user);
}
