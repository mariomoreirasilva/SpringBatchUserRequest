package com.devsuperior.user_rquest_springbatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devsuperior.user_rquest_springbatch.entities.User;

@Configuration
public class InsertUserDataDBWriterConfig {
	
	@Bean
	public ItemWriter<User> insertUserDataDBWriter(){
		return users -> users.forEach(System.out::println);
	}

}
