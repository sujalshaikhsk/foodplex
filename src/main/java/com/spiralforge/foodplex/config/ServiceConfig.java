package com.spiralforge.foodplex.config;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spiralforge.foodplex.payment.PaymentFactory;

@Configuration
public class ServiceConfig {

	 
    @Bean
    public ServiceLocatorFactoryBean serviceLocatorBean(){
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(PaymentFactory.class);
        return bean;
    }
    
}
