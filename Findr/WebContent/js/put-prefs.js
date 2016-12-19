$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || "");
        } else {
        	if (this.name === "name") {
        		var nameArray = this.value.split(" ");
        		var firstName = nameArray[0];
        		var lastName = nameArray[nameArray.length-1];
        		o["firstName"] = firstName;
        		o["lastName"] = lastName;
        	} else {
        		o[this.name] = this.value || "";
        	}
        }
    });
    return o;
};

$(document).ready(function() {
	$('form').submit(function(event){
		console.log("AJAX function called");
		var formData = JSON.stringify($('form').serializeObject());
		console.log(formData);
		$.ajax({
          url: '/Findr/prefs',
          type: 'POST',
          dataType: 'json', 
          data: formData,
          contentType: 'application/json; charset=utf-8',
          complete: function() {
        	  window.location.replace("/Findr/fight");
        	  return false;
          }
		});
		event.preventDefault();
    });
});