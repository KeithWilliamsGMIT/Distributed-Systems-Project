package ie.gmit.sw.job.server;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Handle HTTP requests sent to the /dictionary endpoint.
 */
@Path("/dictionary")
@Singleton
public class DictionaryResource {
	/*
	 * Keep a counter of the client requests.
	 * This will be used to allocate job numbers.
	 */
	private int counter = 0;
	
	/**
	 * This method handles POST requests. Creates a new request and queues it for processing.
	 * @param query, representing a word or phrase, to search for in the dictionary.
	 * @return JSON containing a message which can be displayed to the user and the job number for the request.
	 */
	@POST
	@Path("/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> postRequest(@PathParam("query") String query) {
		// Add request to inQueue.
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", String.format("Searching for '%s'...", query));
		response.put("number", counter);
		
		// Increment the counter for next request.
		counter++;
		
		return response;
	}
}
