package com.ccg.springmvc.sso.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.springmvc.sso.entities.ClientInfoVo;
import com.ccg.springmvc.sso.entities.MockDataBase;

@Controller
public class SSOServerController {

	@RequestMapping("/checkLogin")
	public String checkLogin(String redirectUrl,HttpSession session,Model model){
		
		//1、 判断是否有全局会话
		String token = (String) session.getAttribute("token");
		if(StringUtils.isEmpty(token)){
			//没有全局会话，则跳转到统一认证中心的登录界面
			model.addAttribute("redirectUrl", redirectUrl);
			return "login";
		}else{
			//有可能是缓存带过来的token，则要重新登录
			String verifyToken = verifyToken(token,null,null);
			if(!"true".equals(verifyToken)){
				model.addAttribute("redirectUrl", redirectUrl);
				return "login";
			}
			//有全局会话，有令牌信息，可能是其他系统登录过后保存的。
			//带走token并重定向到原地址
			model.addAttribute("token", token);
			return "redirect:" + redirectUrl;
		}
	}
	
	/**
	 * 登录功能
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String username,String password,String redirectUrl,HttpSession session,Model model){
		if("admin".equals(username) && "123456".equals(password)){
			//匹配成功以后
			//1、创建令牌信息
			String token = UUID.randomUUID().toString();
			//2、创建全局会话，把令牌信息存入会话中
			session.setAttribute("token", token);
			//3、令牌信息存入数据库，先用静态set变量模拟数据库表T_TOKEN。
			MockDataBase.T_TOKEN.add(token);
			//4、重定向到原来的地址，并带上token令牌。www.crm.com:8088/crm/main?token=...
			model.addAttribute("token",token);
			return "redirect:" + redirectUrl;
		}
		//如果密码有误，重新回到登录界面，并带上redirectUrl参数
		model.addAttribute("redirectUrl", redirectUrl);
		return "login";
	}
	/**
	 * 验证token是否有效
	 * @return
	 */
	@RequestMapping("/verify")
	@ResponseBody  //加上此，则表示返回值以String的形式返回即可；不加，则表示返回一个视图；
	public String verifyToken(String token,String clientLogOutUrl,String jsessionId){
		if(MockDataBase.T_TOKEN.contains(token)){
			if(StringUtils.isNotBlank(clientLogOutUrl) && StringUtils.isNotBlank(jsessionId)){
				//把客户端的登出地址记录起来
				List<ClientInfoVo> clientInfoList = MockDataBase.T_CLIENT_INFO.get(token);
				if(clientInfoList == null){
					clientInfoList = new ArrayList<ClientInfoVo>();
					MockDataBase.T_CLIENT_INFO.put(token, clientInfoList);
				}
				clientInfoList.add(new ClientInfoVo(clientLogOutUrl,jsessionId));
			}
			return "true";
		}
		return "false";
	}
	
	@RequestMapping("/logOut")
	public String logOut(HttpSession session){
		//1、销毁全局会话session
		session.invalidate();
		//2、获取子系统的session，并依次销毁。大session销毁，则子系统都销毁，放到监听事件中去
		
		return "logOut";
	}
}









