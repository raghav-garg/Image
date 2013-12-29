<%@page import="com.ipu.imagedropbox.model.ImageDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="java.util.List"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
	<%
		if (request.getAttribute("edit") != null) {
	%>
	<h3>Edit your image</h3>
	<%
		} else {
	%>
	<h3>Choose a image to Upload in Server</h3>
	<%
		}
	%>
	<div>


		<form action="Upload" method="post" enctype="multipart/form-data">

			<input type="file" name="file" /> <input type="submit"
				value="upload" />

		</form>

	</div>


	<%
		if (request.getAttribute("edit") == null) {
			if (request.getAttribute("uploaded") != null) {
				@SuppressWarnings("unchecked")
				List<ImageDetails> imgList = (List<ImageDetails>) session
						.getAttribute("list");
	%>
	<table border=3>
		<tr>
			<th>Name</th>
			<th>Size</th>
			<th>Preview</th>
			<th>delete</th>
			<th>edit</th>
		</tr>
		<%
			for (ImageDetails item : imgList) {
		%>
		<tr>
			<td><%=item.getImageName()%></td>
			<td><%=item.getImageSize()%></td>
			<td><a href=<%=item.getImagePath()%>> <img
					src=<%=item.getImagePath()%> width=50 height=50 />
			</a></td>
			<td>
				<form action=DeleteImage method=POST>
					<input type=hidden value=<%=item.getImageId()%> name=hidden>
					<input type=submit value=delete>
				</form>
			</td>
			<td>
				<form action=EditImage method=post>
					<input type=hidden value=<%=item.getImageId()%> name=hidden /> <input
						type=submit value=edit>
				</form>
			</td>
		</tr>
		
		<%
			}
				}
			}
		%>
	</table>
</body>
</html>