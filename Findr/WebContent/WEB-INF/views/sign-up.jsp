<!DOCTYPE HTML>
<html>
<head>
 	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<title>Test handle form submission</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="js/put-user.js"></script>
</head>
<body>
	<h1>Form</h1>
	
	<form id="signUpForm" action="/Findr/sign-up" method="POST">
		<p> Email: <input type="text" name="email"/> </p>
		<p> First name: <input type="text" name="firstName"/></p>
		<p> Last name: <input type="text" name="lastName"/></p>
		<p> Age: <input type="number" name="age"/></p>
		<p> Gender: <input type="number" name="gender"/></p>
		<p> Height: <input type="number" name="height" /></p>
		<p> Weight: <input type="number" name="weight"/></p>
		<p> Fighting Style: <input type="number" name="fightStyle"/></p>
		<p> Location: <input type="number" name="location"/></p>
		<p><input type="submit" value="Submit" /> <input type="reset" value="Reset"/></p>
	</form>


</body>
</html>