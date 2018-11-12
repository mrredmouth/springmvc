package com.ccg.springmvc.sso.entities;

import lombok.Data;

@Data
public class ClientInfoVo {
	private String clientLogOutUrl;
	private String jsessionId;
	
	public ClientInfoVo(){}
	public ClientInfoVo(String clientLogOutUrl,String jsessionId){
		this.clientLogOutUrl = clientLogOutUrl;
		this.jsessionId = jsessionId;
	}
}
