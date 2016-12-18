<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
<html>
<head>
  <title>Findr</title>
  <spring:url value="/assets/style.css" var="styleCSS" />
  <link href="${styleCSS}" rel="stylesheet" />
  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="js/login.js"></script>
</head>
<body>
    <nav class="navbar navbar-default" role="navigation">
      <div class="navbar-header">

        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
           <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
        </button> <a class="navbar-brand" href="/Findr/">Findr</a>
      </div>

      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li class="active">
            <a href="#">Matches</a>
          </li>
          <li>
            <a href="#">Settings</a>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li>
            <a href="/Findr/">Register</a>
          </li>
        </ul>
      </div>

    </nav>
    <div class="col-sm-6 col-sm-offset-3">

     <h1><span class="fa fa-sign-in"></span>Login</h1>

     <!-- Login -->
     <form action="/Findr/login" method="post">
         <div class="form-group">
             <label>Email</label>
             <input type="text" class="form-control" name="email">
         </div>
         <div class="form-group">
             <label>Password</label>
             <input type="password" class="form-control" name="password">
         </div>

         <button type="submit" class="btn btn-warning btn-lg">Login</button>
     </form>

     <hr>

     <p>Need an account? <a href="/Findr/">Signup</a></p>

 </div>

</body>
</html>