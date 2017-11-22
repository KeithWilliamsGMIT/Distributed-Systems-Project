$(document).ready(function() {
	$('#form').submit(function(event) {
		event.preventDefault();
			
		$.ajax({
			url: '/job-server/webapi/dictionary/' + $('#query').val(),
			type: 'POST',
			success: function(data) {
				
			},
			error: function(data) { }
		});
		
		/*
		 * Reset the form for the next entry.
		 * Adapted from https://stackoverflow.com/questions/8701812/clear-form-after-submission-with-jquery
		 */
		$("form").trigger("reset");
	});
});