package com.co.example.base.generator;

import org.springframework.context.annotation.Bean;

import com.co.example.base.generator.DistributedIdGenerator;
import com.co.example.base.generator.IdGenerator;

public class NextId {
	
	@Bean
	public static Long getId() {
		IdGenerator gen = new DistributedIdGenerator();
		return gen.next();
	}
	
}
