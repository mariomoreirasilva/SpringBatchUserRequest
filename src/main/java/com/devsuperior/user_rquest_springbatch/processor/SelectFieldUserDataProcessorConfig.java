package com.devsuperior.user_rquest_springbatch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devsuperior.user_rquest_springbatch.dto.UserDTO;
import com.devsuperior.user_rquest_springbatch.entities.User;

@Configuration
public class SelectFieldUserDataProcessorConfig {

	private static Logger logger = LoggerFactory.getLogger(SelectFieldUserDataProcessorConfig.class);
	private int contador;
	
	//metodo para pegar uma projeção do dto e colocar na entidade. Pegar alguns campos. Somente isso como exemplo.
	@Bean
	public ItemProcessor<UserDTO, User> selectFieldUserDataProcessor(){
		return new ItemProcessor<UserDTO, User>(){

			@Override
			public User process(UserDTO item) throws Exception {
				User user = new User();
				user.setLogin(item.getLogin());
				user.setName(item.getName());
				user.setAvatarUrl(item.getAvatarUrl());
				logger.info("Processamento de campos do UserDTO para a entidade User " + contador + " - " + user);
				contador++;
				return user;
			}
			
		};
	}
}
