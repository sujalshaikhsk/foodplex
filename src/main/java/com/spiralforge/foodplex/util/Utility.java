package com.spiralforge.foodplex.util;

public class Utility {
	public Utility() {
	}
	
	public static Double getTotalPrice(Integer quantity, Double price) {
		return price*quantity;
	}
}
