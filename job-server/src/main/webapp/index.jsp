<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Dictionary Service</title>
		<link href="css/app.css" type="text/css" rel="stylesheet"></link>
	</head>
	
	<body>
		<h1>Dictionary Service</h1>
		
		<form id="form">
			<input type="text" name="query" id="query"/>
			<input type="submit" value="submit"/>						
		</form>
		
		<p id="message">Waiting for response...</p>
		
		<script
			src="https://code.jquery.com/jquery-3.2.1.min.js"
			integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
			crossorigin="anonymous"></script>
  		<script src="js/app.js" type="text/javascript"></script>
	</body>
</html>