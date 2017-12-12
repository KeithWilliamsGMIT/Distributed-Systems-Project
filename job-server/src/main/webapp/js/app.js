$(document).ready(function() {
	var form = $('#form');
	var waitMessage = $('#wait-message');
	var response = $('#response');
	var definitionMessage = $('#definition-message');
	var resetButton = $('#reset');
	var jobNumber;
	var timer;
	
	/*
	 * Send request to the servlet using AJAX when the form is submitted.
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
				poll();
				
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
				if (data.ready) {
					// Stop calling the poll method.
					clearInterval(timer);
					
					waitMessage.hide();
					
					response.show();
					
					// Output the definition.
					definitionMessage.html(data.definition);
				}
			},
			error: function(data) { }
		});
	}
	
	/*
	 * Reset the page to allow the user to make another query.
	 */
	resetButton.click(function(event) {
		response.hide();
		
		form.show();
	});
});