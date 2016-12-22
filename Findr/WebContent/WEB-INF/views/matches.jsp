<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  <title>Findr</title>
  <spring:url value="/assets/style.css" var="styleCSS" />
  <link href="${styleCSS}" rel="stylesheet" />

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <!-- <script src="js/matches.js"></script> -->
</head>
<body>
    <nav class="navbar navbar-default" role="navigation">
      <div class="navbar-header">

        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
           <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
        </button> <a class="navbar-brand" href="/">Findr</a>
      </div>

      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li class="active">
            <a href="/matches">Matches</a>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li>
            <a href="/login">Logout</a>
          </li>
        </ul>
      </div>

    </nav>
    <div class="container-fluid">
    	<div class="row">
    		<div class="col-md-12 match-header">
            <h1>Matches:</h1>
    		</div>
    		
        <div class="col-md-6 col-md-offset-3">
        	<ul>
        		<c:forEach var="user" items="${allMatches}" >
        			<li>${user}</li>
        		</c:forEach>
        	</ul>

        </div>

    	</div>
    </div>
</body>
</html>
