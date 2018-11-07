package com.ccg.springmvc.entities;

import lombok.Data;

@Data
public class User {

	//id不能被修改，使用ModelAttribute注解
	private Integer id;
	private String username;
	private String password;
	private Integer age;
	private Address address;
	
	public User(){}

	public User(String username, Integer age) {
		this.username = username;
		this.age = age;
	}

	public User(Integer id, String username, String password, Integer age) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
	};
	
}
