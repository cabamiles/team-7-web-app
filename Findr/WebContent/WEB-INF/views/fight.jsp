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
  <script src="js/fight.js"></script>
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
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li>
            <a href="/Findr/login">Logout</a>
          </li>
        </ul>
      </div>

    </nav>
<div class="container">
 <div id="curr" class="content">
   <div class="col-sm-12 pic">
    <img id="main-pic" class="prof-pic" src="${candidatePhoto}"/>
    <div class="bio">
      <div class="col-sm-3 bio-main">
        <h1 id="name">${candidateName}</h2>
        <h2 style="text-align:center;" id="age">${candidateAge}</h2>
      </div>
      <div class="col-sm-9 bio-info">
        Fight Style:<p id="fightStyle">${candidateStyle}</p>
        Height:<p id="height">${candidateHeight}</p>
        Weight:<p id="weight">${candidateAge}</p>
      </div>
    </div>
  </div>
   <div class="swipe col-sm-6">
     <button id="leftClick"><img src="/Findr/assets/x2.png"/></button>
   </div>
   <div id="right-click-div" class="swipe col-sm-6" data-candidate-id="${candidateId}">
     <button id="rightClick"><img src="/Findr/assets/boxing.png"/></button>
   </div>
 </div>
</div>
</body>
</html>