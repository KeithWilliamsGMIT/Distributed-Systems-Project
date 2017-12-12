$(document).ready(function() {
	var form = $('#form');
	var waitMessage = $('#wait-message');
	var definitionMessage = $('#definition-message');
	var jobNumber;
	var timer;
	
	/*
	 * Send request to the servlet using AJAX when the form is submitted
	 */
	form.submit(function(event) {
		event.preventDefault();
			
		$.ajax({
			url: '/job-server/webapi/dictionary/' + $('#query').val(),
			type: 'POST',
			success: function(data) {
				form.hide();
				waitMessage.show();
				jobNumber = data.number;
				
				// Call the poll function immediately.
				poll()
				
				// Call the poll function every 10 seconds.
				timer = setInterval(poll, 10000);
			},
			error: function(data) { }
		});
		
		/*
		 * Reset the form for the next entry.
		 * Adapted from https://stackoverflow.com/questions/8701812/clear-form-after-submission-with-jquery
		 */
		form.trigger('reset');
	});
	
	function poll () {
		$.ajax({
			url: '/job-server/webapi/dictionary/' + jobNumber,
			type: 'GET',
			success: function(data) {
				// Stop calling the poll method.
				clearInterval(timer);
				
				waitMessage.hide();
				
				// Output the definition.
				definitionMessage.html(data.definition)
			},
			error: function(data) { }
		});
	}
});