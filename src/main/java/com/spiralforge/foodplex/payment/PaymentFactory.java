package com.spiralforge.foodplex.payment;

public interface PaymentFactory {
	
	Payment getPaymentMethod(String paymentType);

}
