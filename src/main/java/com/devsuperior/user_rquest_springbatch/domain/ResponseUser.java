package com.devsuperior.user_rquest_springbatch.domain;

import java.util.List;

import com.devsuperior.user_rquest_springbatch.dto.UserDTO;

public class ResponseUser {
	
	private List<UserDTO> content; //importante colocar o nome content para corresponder ao retorno do get no postman

	public List<UserDTO> getContent() {
		return content;
	}
	
	 
}
