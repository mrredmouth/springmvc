package com.ccg.springmvc.handlers;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ccg.springmvc.entities.User;


/**
 * @ RequestMapping可以注解类和方法：
 * 		整体的浏览器URL = 类RequestMapping + 方法RequestMapping，类上没有则直接通过方法上的即可访问。
 * @ SessionAttributes：将哪些request域中的属性，放到session域中
 * 		value属性：通过属性名key指定，放入user名的属性
 * 		type属性：通过属性值value的类型指定，放入value值为String类型的属性
 * 		只能放在类上面，不能放在方法上面
 */
@SessionAttributes(value={"user"},types={String.class})
@RequestMapping("/mine")
@Controller
public class TestSpringMVCHandlers {
	
	private static final String SUCCESS = "success";
	
	/**
	 * 使用@RequestMapping注解，映射请求：href="helloWorld"
	 * return "success":解析成物理视图 = /WEB-INF/views/success.jsp,
	 * 然后做转发操作
	 * @return
	 */
	@RequestMapping("/helloWorld")
	public String hello(){
		System.out.println("Hello World");
		return SUCCESS;
	}
	/**
	 * 使用method指定请求方式，a链接为get请求，无法请求此方法。可以用表单指定方式请求至此
	 */
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("TestSpringMVC.testMethod()");
		return SUCCESS;
	}
	
	/**
	 * params：请求参数必须包含参数username，必须包含参数age且值不等于10，必须不包含参数email
	 * headers: 请求头里的Accept-Language属性必须为zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
	 * @return
	 */
	@RequestMapping(value="/testParamsAndHeaders",params={"username","age!=10","!email"},
			headers="Accept-Language=zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
	public String testParamsAndHeaders(){
		System.out.println("TestSpringMVC.testParamsAndHeaders()");
		return SUCCESS;
	}
	/**
	 * @ PathVariable 可以将URL中占位符参数绑定到handler方法的入参里。
	 * 参数里面@PathVariable(value="id")，value是默认属性，可以写成@PathVariable("id")
	 */
	@RequestMapping(value="/testPathVariable/{id}")
	public String testPathVariable(@PathVariable(value="id")Integer id1){
		System.out.println("TestSpringMVC.testPathVariable():"+id1);
		return SUCCESS;
	}
	
	/**
	 * REST风格URL：GET,POST,PUT,DELETE
	 * 				   /order/1		HTTP GET：得到id=1的order
					   /order/1		HTTP DELETE：删除id=1的order
					   /order/1		HTTP PUT：更新id=1的order
					   /order		HTTP POST：新增order
	 * 发送GET，POST请求：正常表单就可以；
	 * 发送PUT，DELETE请求：需要POST请求，且加上_method的隐藏域，
	 * 		然后通过HiddenHttpMethodFilter过滤得到PUT，DELETE请求
	 * 需要在web.xml中配置过滤器
	 */
	@RequestMapping(value="/testRest",method=RequestMethod.PUT)
	public String testRestPut(){
		System.out.println("RestPut");
		return SUCCESS;
	}
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.DELETE)
	public String testRestDelete(@PathVariable(value="id")Integer id){
		System.out.println("DELETE："+id);
		return SUCCESS;
	}
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.POST)
	public String testRestPost(@PathVariable(value="id")Integer id){
		System.out.println("POST："+id);
		return SUCCESS;
	}
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.GET)
	public String testRestGet(@PathVariable(value="id")Integer id){
		System.out.println("Get："+id);
		return SUCCESS;
	}
	
	/**
	 * @ RequestParam 获取请求参数的信息，将入参传递到方法中，
	 * age参数可为空可不为空，int类型时不能传null，要给默认值。Integer类型可以传null
	 */
	@RequestMapping(value="/testRequestParam")
	public String testRequestParam(@RequestParam(value="username")String un,
			@RequestParam(value="age",required=false,defaultValue="0")int age,
			@RequestParam(value="age2",required=false)Integer age2){
		System.out.println("testRequestParam："+un+","+age+","+age2);
		return SUCCESS;
	}
	/**
	 * @ RequestHeader 获取请求头里的信息
	 */
	@RequestMapping(value="/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value="Accept-Language")String al){
		System.out.println("testRequestHeader，  Accept-Language："+al);
		return SUCCESS;
	}
	/**
	 * @ CookieValue 获取cookie里面的值
	 */
	@RequestMapping(value="/testCookieValue")
	public String testCookieValue(@CookieValue(value="JSESSIONID")String jSessionId){
		System.out.println("testCookieValue，  JSESSIONID："+jSessionId);
		return SUCCESS;
	}
	
	/**
	 * 使用POJO对象绑定请求参数：
	 * 	springMVC会按照请求参数名和POJO属性名进行匹配，自动得到填充好请求参数的对象，而且支持联级属性，如address.province
	 */
	@RequestMapping(value="/testPOJO")
	public String testPOJO(User user){
		System.out.println("testPOJO，  User："+user);
		return SUCCESS;
	}
	
	/**
	 * 可以使用servlet原生的API作为handler的参数，
	 * 支持API：HttpServletRequest,HttpServletResponse,HttpSession,
	 * java.security.Principal,Loacl InputStream,OutputStream,Reader,Writer
	 * 
	 */
	@RequestMapping(value="/testServletAPI")
	public String testServletAPI(HttpServletRequest req,HttpServletResponse resp){
		System.out.println("testServletAPI，  req："+req +",resp:"+resp);
		return SUCCESS;
	}
	@RequestMapping(value="/testServletAPIWriter")
	public void testServletAPIWriter(HttpServletRequest req,HttpServletResponse resp,Writer out) throws IOException{
		out.write("hello testServletAPIWriter");
	}
	
	/**
	 * 传递model方式一：ModelAndView
	 * 返回值类型是ModelAndView类型：包含视图view和模型model
	 * SpringMVC会将model的数据放到request域对象中
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/testModelAndView")
	public ModelAndView testModelAndView(){
		ModelAndView modelAndView = new ModelAndView();
		//设置视图
		modelAndView.setViewName(SUCCESS);
		//设置模型，model数据会放到request域对象中
		modelAndView.addObject("time",new Date());
		return modelAndView;
	}
	/**
	 * 传递model方式二：Map类型参数
	 * 目标方法添加Map类型参数，也可以是Model类型、ModelMap类型。
	 */
	@RequestMapping(value="/testMap")
	public String testMap(Map<String,Object> map){
		//map对象共享到SUCCESS返回的页面中。map对象实际放到了ModelAndView对象的ModelMap中去，ModelAndView放在request中
		map.put("names", Arrays.asList("lily","tom","jerry"));
		return SUCCESS;
	}
	/**
	 * 传递model方式二：Map类型参数
	 * 目标方法添加Map类型参数，也可以是Model类型、ModelMap类型。
	 */
	@RequestMapping(value="/testSessionAttributes")
	public String testSessionAttributes(Map<String,Object> map){
		//map放在request域对象中。
		//同时，在类上面加SessionAttributes注解value={"user"}，里面放入user，则request里名为user的属性也会放到session中
		map.put("user", new User("Tomy",25));
		//因为types={String.class}，value为String类型的也放到session中
		map.put("school", "atguigu");
		return SUCCESS;
	}
	
	/**
	 * id:对应传进来表单的字段id，可以为空。
	 * map:会放到ModelAndView中.
	 * 加了@ModelAttribute的方法，会在每一个handler方法之前执行,作为Model的一个属性,运行流程：
	 * 		1、@ModelAttribute注解的方法，从数据库取出对象，放入map中，key为"user";
	 * 		2、SpringMVC从Map中取数user对象，并将请求参数赋给对应的属性；
	 * 		3、SpringMVC将赋值后的对象传入目标方法中，以入参形式。
	 * 注意：@ModelAttribute修饰的方法中，数据库取出的对象放入map时的键key的名，必须与入参对象类型名相同，首字母小写。
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false)Integer id,Map<String ,Object> map){
		if(id != null){
			//模拟从数据库获取数据
			User user = new User(1,"Tom","123456",29);
			System.out.println("数据库获取的对象："+user);
			map.put("user", user);
		}
	}
	/**
	 * 要修改的user：表单提交里面没有password字段，修改时就会置空，
	 * 要做到没传入不修改，先从数据库获取这条数据，然后往这个数据对象里面插入表单提交的字段，这样未插入的字段数据库数据也带上了。
	 * @ ModelAttribute先执行了，所以要修改的user带上了password属性
	 */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user){
		System.out.println("要修改的user：" + user);
		return SUCCESS;
	}
}
