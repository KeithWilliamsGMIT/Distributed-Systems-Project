package ie.gmit.sw.job.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import ie.gmit.sw.request.Request;
import ie.gmit.sw.request.Requestable;

/**
 * Handle HTTP requests sent to the /dictionary endpoint.
 */
@Path("/dictionary")
@Singleton
public class DictionaryResourceImpl implements DictionaryResource {
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
	 * Constructor to create threads for processing requests.
	 */
	public DictionaryResourceImpl() {
		Thread rmiClientThread = new Thread(new RmiClientThread(queue));
		rmiClientThread.start();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> postRequest(String query) throws InterruptedException {
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