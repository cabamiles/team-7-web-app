var styleMap = {"1":"Judo", "2":"Boxing", "3":"Muay Thai", "1":"Wrestling"};
var genderMap = {"1":"Male", "2":"Female"};

$(document).ready(function() {
	$("#leftClick").click(function(event){
		$.ajax({
          url: '/Findr/fight-left',
          type: 'GET',
          dataType: 'json',
          success: function(data) {
        	  var firstName = data['FirstName'];
        	  var height = data['Height'];
        	  var weight = data['Weight'];
        	  var gender = genderMap[data['Gender']];
        	  var age = data['Age'];
        	  var style = styleMap[data['FightingStyle']];
        	  var picSrc;
        	  
        	  if (gender === "Male") {
        		  picSrc = "http://blogs-images.forbes.com/kurtbadenhausen/files/2015/06/fm-e1433941678273.jpg";
        	  } else {
        		  picSrc = "https://peopledotcom.files.wordpress.com/2016/08/rhonda-rousey-435.jpg";
        	  }
        	  
        	  
        	  
        	  $("#curr").fadeOut(800, function(){
        			  $("#main-pic").attr("src", picSrc).fadeIn();
        			  $("#name").text(firstName).fadeIn();
                	  $("#fightStyle").text(style).fadeIn();
                	  $("#height").text(height).fadeIn();
                	  $("#weight").text(weight).fadeIn();
                	  $("#age").text(age).fadeIn();
                	  $("#curr").fadeIn();
        	  });
          }
		});
    });
});

$(document).ready(function() {
	$("#rightClick").click(function(event){
		$.ajax({
          url: '/Findr/fight-right',
          type: 'GET',
          success: function(data) {
        	  var firstName = data['FirstName'];
        	  var height = data['Height'];
        	  var weight = data['Weight'];
        	  var gender = genderMap[data['Gender']];
        	  var age = data['Age'];
        	  var style = styleMap[data['FightingStyle']];
        	  var picSrc;
        	  
        	  if (gender === "Male") {
        		  picSrc = "http://blogs-images.forbes.com/kurtbadenhausen/files/2015/06/fm-e1433941678273.jpg";
        	  } else {
        		  picSrc = "https://peopledotcom.files.wordpress.com/2016/08/rhonda-rousey-435.jpg";
        	  }
        	  
        	  
        	  
        	  $("#curr").fadeOut(800, function(){
        			  $("#main-pic").attr("src", picSrc).fadeIn();
        			  $("#name").text(firstName).fadeIn();
                	  $("#fightStyle").text(style).fadeIn();
                	  $("#height").text(height).fadeIn();
                	  $("#weight").text(weight).fadeIn();
                	  $("#age").text(age).fadeIn();
                	  $("#curr").fadeIn();
        	  });
          }
		});
    });
});