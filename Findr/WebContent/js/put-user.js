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
            o[this.name] = this.value || "";
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
          url: '/Findr/sign-up',
          type: 'POST',
          dataType: 'json', 
          data: formData,
          contentType: 'application/json; charset=utf-8',
		})
			.done(function() {
				console.log("success");
			});
		event.preventDefault();
    });
});