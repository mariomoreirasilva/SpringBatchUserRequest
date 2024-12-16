package com.devsuperior.user_rquest_springbatch.entities;

public class User {

	private String Login;
	private String name;
	private String avatarUrl;
	
	public User() {
		
	}

	public User(String login, String name, String avatarUrl) {
		super();
		Login = login;
		this.name = name;
		this.avatarUrl = avatarUrl;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Override
	public String toString() {
		return "User [Login=" + Login + ", name=" + name + ", avatarUrl=" + avatarUrl + "]";
	}
	
	
}
