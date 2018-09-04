<html>
<body>
<h2>Hello World!</h2>

<a href = "helloworld">hello world</a>
<br>
<a href = "springmvc/testRequestMapping">test RequestMapping</a>
<br></br>
<a href="springmvc/testMethod">testMethod</a>

<br></br>

<form action="springmvc/testMethod" method = "post">
	<input value = "submit" type = "submit"/>
</form>

<br><br>
<a href="springmvc/testParamsAndheaders?username=qq&age = 10">test ParamsAndheaders</a>

<br><br>
<a href="springmvc/testPath/cacaca/abc">test Path</a>

<br><br>
<a href="springmvc/testPathVariable/1">test PathVariable</a>

<br><br>
<a href="springmvc/testRest/1">test Rest GET</a>

<br><br>
<form action="springmvc/testRest" method = "post">
	<input type = "submit" value = "submit">
</form>
<br><br>
<form action="springmvc/testRest/1" method = "post">
	<input type = "hidden" name = "_method" value = "PUT">	
	<input type = "submit" value= "PUT">
</form>
<br><br>
<form action="springmvc/testRest/1" method = "post">
	<input type = "hidden" name = "_method" value = "DELETE">
	<input type = "submit" value = "DELETE">
</form>
<br><br>
<a href="springmvc/testRequestParam?username=harry&age=18">test RequestParam</a>

<br><br>
<a href="springmvc/testRequestHeader">test RequestHeader</a>

<br><br>
<a href="springmvc/testCookieValue">test CookieValue</a>

<br><br>
<form action="springmvc/testPojo" method = "post">
	Username: <input type = "text" name = "username"> <br>
	Password: <input type = "password" name = "password"> <br>
	Email: <input type = "email" name = "email"><br> 
	Age : <input  type ="text"  name = "age"><br>
	Province :<input type = "text" name = "Address.Province"><br>
	City:<input type ="text" name = "Address.City"><br>
	<input type="submit" value = "sumbit">
</form>

</body>
</html>
