package com.example.shopping;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingConfig {
	@Bean
	public ModelMapper modelMappingBean() {
		return new ModelMapper();
	}
}
