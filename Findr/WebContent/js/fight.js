var styleMap = {"0":"Judo", "1":"Boxing", "2":"Muay Thai", "3":"Wrestling"};
var genderMap = {"0":"Male", "1":"Female"};

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
	$("#rightClick").click(function(event) {
		var candidateId = $("#right-click-div").data("candidate-id");
		console.log(candidateId);
		$.ajax({
	          url: '/Findr/check-match',
	          type: 'POST',
	          dataType: 'json', 
	          data: candidateId,
	          contentType: 'application/json; charset=utf-8',
	          complete: function() {
	        	  // redirect to get request
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
	          }
		});
	});
});

//$(document).ready(function() {
//	$("#rightClick").click(function(event){
//		$.ajax({
//          url: '/Findr/fight-right',
//          type: 'GET',
//          success: function(data) {
//        	  var firstName = data['FirstName'];
//        	  var height = data['Height'];
//        	  var weight = data['Weight'];
//        	  var gender = genderMap[data['Gender']];
//        	  var age = data['Age'];
//        	  var style = styleMap[data['FightingStyle']];
//        	  var picSrc;
//        	  
//        	  if (gender === "Male") {
//        		  picSrc = "http://blogs-images.forbes.com/kurtbadenhausen/files/2015/06/fm-e1433941678273.jpg";
//        	  } else {
//        		  picSrc = "https://peopledotcom.files.wordpress.com/2016/08/rhonda-rousey-435.jpg";
//        	  }
//        	  
//        	  
//        	  
//        	  $("#curr").fadeOut(800, function(){
//        			  $("#main-pic").attr("src", picSrc).fadeIn();
//        			  $("#name").text(firstName).fadeIn();
//                	  $("#fightStyle").text(style).fadeIn();
//                	  $("#height").text(height).fadeIn();
//                	  $("#weight").text(weight).fadeIn();
//                	  $("#age").text(age).fadeIn();
//                	  $("#curr").fadeIn();
//        	  });
//          }
//		});
//    });
//});