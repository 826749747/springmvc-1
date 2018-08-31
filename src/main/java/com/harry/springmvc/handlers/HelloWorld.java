package com.harry.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	/*
	 * 1.使用@RequestMapping 注解来映射请求的URL
	 * 2.返回值会通过视图解释器解析为实际的物理视图，对于InternalResourceViewResolver 视图解析器，会做如下的解析：
	 * prefix + returnval + 后缀 这样的方式得到实际的物理视图，然后做转发操作
	 * 
	 * /WEB-INF/views/success.jsp
	 * */
	@RequestMapping("/helloworld")
	public String Hello() {
		System.out.println("Hello World!");
		return "success";
	}
}
