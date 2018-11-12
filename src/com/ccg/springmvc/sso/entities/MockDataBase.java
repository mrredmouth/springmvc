package com.ccg.springmvc.sso.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MockDataBase {
	
	//模拟数据库表，存储token信息，用于多系统之间共享
	public static Set<String> T_TOKEN = new HashSet<String>();
	//模拟数据库，用于登出销毁session，存储token对应的各个系统的信息，包括JSessionId,登出的URL
	public static Map<String,List<ClientInfoVo>> T_CLIENT_INFO = new HashMap<String,List<ClientInfoVo>>();
}
