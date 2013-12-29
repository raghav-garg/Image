<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="com.ipu.imagedropbox.model.ImageDetails"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<% ImageDetails editItem = (ImageDetails)session.getAttribute("editItem");%>
<h3>Edit your Image</h3><br/>
<form method=post action=EditName >

<center>
<img src=<%= editItem.getImagePath()%> width=150 height=150 />
</center>
Enter name of image to edit:<input type=text name=newName value=<%= editItem.getImageName() %>/>
<input type="submit" value="submit"/>
</form>

<form method=post action=ChangeImage enctype= "multipart/form-data">
Your new image: <input type=file name=file/>
<input type=submit value= submit />
</form>


</body>
</html>