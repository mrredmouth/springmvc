package com.ccg.springmvc.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccg.springmvc.entities.MockDataBase;

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
			String verifyToken = verifyToken(token,model);
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
	public String verifyToken(String token,Model model){
		if(MockDataBase.T_TOKEN.contains(token)){
			return "true";
		}
		return "false";
	}
	
}
