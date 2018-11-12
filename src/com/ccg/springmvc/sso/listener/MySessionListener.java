package com.ccg.springmvc.sso.listener;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ccg.springmvc.sso.entities.ClientInfoVo;
import com.ccg.springmvc.sso.entities.MockDataBase;
import com.ccg.springmvc.sso.utils.HttpUtils;

public class MySessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//依次销毁统一认证中心里面，所有子系统的session
		HttpSession session = se.getSession();
		String token = (String) session.getAttribute("token");
		MockDataBase.T_TOKEN.remove(token);
		//删除token键值对，同时返回建对应的value值
		List<ClientInfoVo> clientInfoVoList = MockDataBase.T_CLIENT_INFO.remove(token);
		try {
			if(clientInfoVoList!=null && clientInfoVoList.size()>0){
				for(ClientInfoVo vo:clientInfoVoList){
					HttpUtils.sendHttpRequest(vo.getClientLogOutUrl(), vo.getJsessionId());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
