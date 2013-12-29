<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
if(request.getAttribute("error")!=null){
%>
Not valid user! Try again
<%
}
 %>

<%
if(request.getAttribute("empty")!=null){
	%>
	Empty fields! Try again
	<% 
}
%>

<form action="Login">
<center><h3>IMAGE UTILITY MANAGEMENT</h3></center>
<table>
<tr><td>Name:<input type="text" name="name" /><br/></tr>
<tr><td>Password:<input type="password" name="pass" /><br/></td></tr>
</table>
<input type="submit" value="login"/>
</form>

