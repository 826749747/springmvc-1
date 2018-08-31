package com.harry.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/springmvc")
@Controller
public class SpringTest {
	
	private static final String SUCCESS = "success";
	
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
