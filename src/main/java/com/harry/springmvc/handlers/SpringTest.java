package com.harry.springmvc.handlers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.harry.springmvc.entities.User;

@RequestMapping("/springmvc")
@Controller
public class SpringTest {
	
	private static final String SUCCESS = "success";
	
	
	/**
	 * SpringMVC 会按请求参数名和POJO 属性名进行自动匹配
	 * 自动为该对象填充属性值。支持级联属性
	 * 如dept.deptId等
	 * */
	@RequestMapping("/testPojo")
	public String testPojo(User user) {
		System.out.println("testPojo : " + user);
		return SUCCESS;
	}
	
	/**
	 * 了解：
	 * @CookieValue : 映射一个Cookie值。属性同@RequestParam 
	 * */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		
		System.out.println("testCookieValue : sessionId : " + sessionId);
		return SUCCESS;
	}
	/**
	 * 了解：
	 * 映射请求头信息
	 * 用法同@RequestParam
	 * */
	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(@RequestHeader("Accept-Language") String AL) {
		
		System.out.println("testRequestHeader , Accept-Language : " + AL);
		return SUCCESS;
	}
	
	/**
	 * @RequestParam 来映射请求参数，
	 * value 值即请求参数的参数名
	 * required 该参数是否必须，默认为true
	 * defaultValue 请求参数的默认值
	 * */
	@RequestMapping("/testRequestParam")
	public String testRequestParam(@RequestParam("username") String username ,
			@RequestParam(value="age", required = false) Integer age) {
		
		System.out.println("testRequestParam...  username : " +username + " age : " + age);
		return SUCCESS;
	}
	
	/**
	 * Rese 风格的URL
	 * 以CRUD 为例：
	 * 新增：/order POST
	 * 修改：/order PUT			update?id=1
	 * 获取：/order GET			get?id=1
	 * 删除：/order DELETE		delete?id=1
	 * 
	 * 如何发送PUT请求和DELETE 请求呢？
	 * 
	 * 1.需要配置 HiddenHttpMethodFilter
	 * 2.需要发送POST请求
	 * 3.需要在发送POST请求携带一个name = "_method" 的隐藏域 ，值为DELETE 或 PUT
	 * 
	 * 在SpringMVC 的目标方法中如何得到id 呢？
	 * 
	 * 使用@PathVariable 注解
	 * */
	@RequestMapping(value="/testRest/{id}",method = RequestMethod.GET)
	public String testRest(@PathVariable("id") Integer id) {
		System.out.println("test Rest GET " + id);
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest",method = RequestMethod.POST)
	public String testRest() {
		System.out.println("test Rest POST.."  );
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest/{id}",method = RequestMethod.PUT)
	public String testRestPut(@PathVariable("id") Integer id) {
		System.out.println("test Rest PUT" + id);
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest/{id}",method = RequestMethod.DELETE)
	public String testRestDelete(@PathVariable("id") Integer id) {
		System.out.println("test Rest Delete" + id);
		return SUCCESS;
	}
	
	
	
	/**
	 * @PathVariable 可以映射URL中的占位符到目标方法的参数中
	 * */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") int id) {
		
		System.out.println("testPathVariable .." + id);
		return SUCCESS;
	}
	
	/**
	 * 使用通配符URL
	 * 1.*代表任意字符
	 * 2.**多层路径
	 * 3.？一个字符
	 * */
	@RequestMapping("/testPath/*/abc")
	public String testPath() {
		
		System.out.println("test path.....");
		return SUCCESS;
	}
	
	/**
	 * 了解可以使用params和headers 来更加精确的映射请求，params和headers支持简单的表达式
	 * */
	@RequestMapping(value = "/testParamsAndheaders" ,params = {"username" , "age = 10"},headers= {"Accept-Language= zh,en-US;q=0.9,en;q=0.8"})
	public String testParamsAndheaders() {
		
		System.out.println("testParamsAndheaders..");
		
		return SUCCESS;
	}
	/*
	 * 常用：使用Method属性来指定请求方式
	 * 
	 * */
	@RequestMapping(method=RequestMethod.POST,value="/testMethod")
	public String testMethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}
	/*
	 * 1.@RequestMapping 除了可以修饰方法，还可以修饰类
	 * 2.
	 * 	 1）类定义处：提供初步的请求映射信息。相对WEB应用的根目录
	 * 	 2）方法处：提供进一步的细分映射信息
	 * 			相对于类定义处的URL。若类定义处未标注@RequestMapping，则方法处标记的URL相对WEB应用的根目录
	 * */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		
		System.out.println("testRequestMapping...");
		return SUCCESS;
	}
}
