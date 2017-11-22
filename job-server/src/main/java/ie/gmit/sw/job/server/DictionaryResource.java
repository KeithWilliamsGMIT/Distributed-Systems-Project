package ie.gmit.sw.job.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ie.gmit.sw.request.Request;
import ie.gmit.sw.request.Requestable;

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
	
	/*
	 * The blocking queue used to queue requests.
	 */
	private BlockingQueue<Requestable> queue = new LinkedBlockingQueue<Requestable>();
	
	/**
	 * This method handles POST requests. Creates a new request and queues it for processing.
	 * @param query, representing a word or phrase, to search for in the dictionary.
	 * @return JSON containing a message which can be displayed to the user and the job number for the request.
	 * @throws InterruptedException 
	 */
	@POST
	@Path("/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> postRequest(@PathParam("query") String query) throws InterruptedException {
		// Add request to inQueue.
		Requestable request = new Request(query, counter);
		queue.put(request);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", String.format("Searching for '%s'...", query));
		response.put("number", counter);
		
		// Increment the counter for next request.
		counter++;
		
		return response;
	}
}
