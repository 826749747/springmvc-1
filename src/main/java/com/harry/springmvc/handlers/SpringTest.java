package com.harry.springmvc.handlers;

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

import com.harry.springmvc.entities.User;

@SessionAttributes(value= {"user"} , types = {String.class})
@RequestMapping("/springmvc")
@Controller
public class SpringTest {
	
	private static final String SUCCESS = "success";
	
	
	
	/**
	 *  1.有@ModelAttribute 标记的方法，会在每个目标方法执行之前被SpringMVC 调用
	 *  2.@ModelAttribute 注解也可以来修饰目标方法POJO类型的入参，其value属性值有如下作用：
	 *  	1）springmvc会使用value属性值在implicitModel中查找对应的对象，若存在则会直接传入到目标方法的入参中
	 *  	2）springmvc会一value为key，POJO类型的对象为value，存入到request中
	 *  
	 * */
	@ModelAttribute
	public void getUser(@RequestParam(value ="id",required =false) Integer id,Map<String,Object> map) {
		if (!"".equals(id)) {
			//a.模拟从数据库中获取对象
			User user = new User(1,"Tom","123456","tom@cc.com",12);
			System.out.println("从数据库获取一个对象 : "+ user );
			map.put("user", user);
		}
	}
	
	/**
	 * 运行流程：
	 * 1.执行@ModelAttribute 注解修饰的方法：从数据库中取出对象，把对象放入到了Map中。键为：user
	 * 2.SpringMVC 从Map中取出User 对象，并把表单的请求参数赋给该User 对象的属性
	 * 3.SpringMVC 把上述对象传入目标方法的参数
	 * 
	 * 注意： 在ModelAttribute修饰的方法中，放入到Map时的键需要和目标方法入参类型的第一个字母小写的字符串的一致！ 
	 * 
	 * SpringMVC 确定目标方法POJO 类型的入参的过程
	 * 1.确定一个key：
	 *   1）若目标方法的POJO类型的参数没有使用@ModleAttribute 作为参数，则key为POJO类名第一个字母的小写
	 *   2）若使用了@ModelAttribute 来修饰，则key为@ModelAttribute注释的value值
	 * 2.在implicitModel 中查找key 对应的对象，若存在，则作为入参传入
	 * 	 1）若在@ModelAttribute标记的方法中在map中保存过，且key和1确定的key一致，则会获取到
	 * 3.若implicitModel 中不存在key对应的对象，则检查当前的Handler 是否使用@SessionAttributes 注解修饰
	 * 	若使用了该注解，且@SessionAttribute 注解的value属性值中包含了key，则会从httpSession中来获取key所对应的Value值，若存在则直接传入
	 *  到目标方法的入参中，若不存在，则抛出异常。
	 * 4.若Handler没有标识@SessionAttributes 注解或@SessionAttributes 注解的value 值中不包含key，则会通过反射来创建POJO 类型的参数，
	 * 	传入为目标方法的参数
	 * 5. SpringMVC 会把key和value（POJO类型的对象） 保存到implicitModle中，进而会保存到request中
	 * 
	 * 
	 * 源码分析的流程：
	 * 1.调用@ModelAttribute 注解修饰的方法。实际上把@ModelAttribute 方法中的map中的数据放在 implicitModel中。
	 * 2.解析请求处理器的目标参数，实际上该目标参数来自于WebDataBinder 对象的target属性
	 * 	 1）创建WebdateBinder对象
	 * 		1.确定objectName的属性：若传入的attrName 属性值为"",则objectName 为类名第一个字母小写
	 * 		  *注意：attrName。 若目标方法的POJO属性使用了@ModelAttribute 来修饰，则attrName值即为@ModelAttribute的value属性值
	 * 		2.确定target的属性
	 * 			> 在implicitModel 中查找attrName 对应的属性值。若存在，ok
	 * 			> *若不存在：则验证当前Handler 是否使用了 @SessionAttribute 进行修饰，若使用了，则尝试从Session 中获取attrName 所对应的属性值。
	 * 			  若session中没有对应的属性值，则抛出了异常
	 * 			> 若Handler 没有使用@SessionAttribute 进行修饰，或@SessionAttribute中没有使用value 值指定的Key和attrName 相匹配，
	 * 			则通过反射创建了POJO对象
	 * 	 2）. SpringMVC 把表单的请求参数赋给了WebDataBinder 的target 对应的属性
	 * 	 3）. *SpringMVC 会把WebDataBinder 的atrrName 和 target 给到implicitModel.进而传到request 域对象中.
	 * 	 4). 把WebDataBinder 的 target 作为参数传递给目标方法入参.
	 * */
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(@ModelAttribute("user") User user) {
		System.out.println("xiugai :" + user);
		return SUCCESS;
	}
	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外（实际上使用的是value 属性值）
	 *  还可以通过模型属性的对象类型指定哪些类型模型属性需要放到会话中（实际上使用的是types 属性值）
	 * */
	@RequestMapping("/testSessionAttribute")
	public String testSessionAttribute(Map<String, Object> map) {
		User user = new User("Tom","123456","tom@cc.com",15);
		map.put("user", user);
		map.put("School", "GDUT");
		return SUCCESS;
	}
	/**
	 * 目标方法可以添加Map 类型（实际上也可以是Model类型或ModelMap类型）的参数
	 * */
	@RequestMapping("/testMap")
	public String testMap(Map<String, Object> map) {
		
		map.put("names", Arrays.asList("Tom","Harry","Jack"));
		return SUCCESS;
	}
	
	/**
	 * 目标方法的返回值可以是ModeAndView 类型
	 * 其中可以包含视图和模型信息
	 *SpringMVC 会把ModelAndView 的model 中数据放入到request 域对象中 
	 * */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		String ViewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(ViewName);
		
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	
	/**
	 * 可以使用Servlet 原生的API作为目标方法的参数，具体支持一下类型
	 * 
	 * HttpServletRequest
	 * HttpServletReponse
	 * HttpSession
	 * java.security.Principal
	 * Locale 
	 * InputStream
	 * OutputStream
	 * Reader
	 * Writer
	 * 
	 * */
	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest request,HttpServletResponse response,Writer out) throws IOException {
		
		System.out.println("testServletAPI..." + request + ", " + response);
		out.write("hello springmvc");
		//return SUCCESS;
	}
	
	
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
