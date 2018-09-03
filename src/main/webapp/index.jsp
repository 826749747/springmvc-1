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
<form action="springmvc/testRest" method = "POST">
	<input type = "submit" value = "submit">
</form>

<br><br>
<form action="springmvc/testRest/1" method = "POST">
	<input type = "hidden" name = "_method" value = "PUT">	
	<input type = "submit" value= "PUT">
</form>

<br><br>
<form action="springmvc/testRest/1" method = "POST">
	<input type = "hidden" name = "_method" value = "DELETE">
	<input type = "submit" value = "DELETE">

</form>

</body>
</html>
