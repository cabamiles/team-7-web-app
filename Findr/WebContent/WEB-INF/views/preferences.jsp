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
  <script src="js/put-prefs.js"></script>
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
            <a href="/Findr/login">Login</a>
          </li>
        </ul>
      </div>

    </nav>

 <div class="col-sm-6 col-sm-offset-3">

    <h1><span class="fa fa-sign-in"></span> Preferences</h1>

    <!-- Signup  -->
    <form action="/Findr/prefs" method="post">
        <div class="form-group">
          <label class="control-label" for="gender">Gender</label>
          <div class="">
            <div class="radio">
              <label for="gender-0">
                <input type="radio" name="gender" id="gender-0" value="1" checked="checked">
                Male
              </label>
          	</div>
            <div class="radio">
              <label for="gender-1">
                <input type="radio" name="gender" id="gender-1" value="2">
                Female
              </label>
          	</div>
          </div>
        </div>
        <div class="form-group">
            <label>Weight</label>
            <input type="text" class="form-control" name="weight">
        </div>
        <div class="form-group">
            <label>Height</label>
            <input type="text" class="form-control" name="height">
        </div>
        <div class="form-group">
            <label>Location</label>
            <input type="text" class="form-control" name="location">
        </div>
        <div class="form-group">
          <label class="control-label" for="fightStyle">Preferred Fighting Stlyes</label>
          <div class="">
          <div class="checkbox">
            <label for="fstyles-0">
              <input type="checkbox" name="fightStyle" id="fstyles-0" value="1">
              Judo
            </label>
        	</div>
          <div class="checkbox">
            <label for="fstyles-1">
              <input type="checkbox" name="fightStyle" id="fstyles-1" value="2">
              Boxing
            </label>
        	</div>
          <div class="checkbox">
            <label for="fstyles-2">
              <input type="checkbox" name="fightStyle" id="fstyles-2" value="3">
              Muay Thai
            </label>
        	</div>
          <div class="checkbox">
            <label for="fstyles-3">
              <input type="checkbox" name="fightStyle" id="fstyles-3" value="4">
              Wrestling
            </label>
        	</div>
          </div>
        </div>
        <div class="form-group">
            <label>Email</label>
            <input type="text" class="form-control" name="email">
        </div>
        <button type="submit" class="btn btn-warning btn-lg">Signup</button>
    </form>

    <hr>

</div>
</body>
</html>
