package ie.gmit.sw.job.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
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
	 * The blocking queue used to queue requests to be processed.
	 */
	private BlockingQueue<Requestable> inQueue = new LinkedBlockingQueue<Requestable>();
	
	/*
	 * The map used to store requests that have been processed.
	 */
	private Map<Integer, String> outQueue = new ConcurrentHashMap<Integer, String>();
	
	
	/**
	 * Constructor to create threads for processing requests.
	 */
	public DictionaryResourceImpl() {
		Thread rmiClientThread = new Thread(new RmiClientThread(inQueue, outQueue));
		rmiClientThread.start();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> postRequest(String query) throws InterruptedException {
		// Add request to the inQueue.
		Requestable request = new Request(query, counter);
		inQueue.put(request);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", String.format("Searching for '%s'...", query));
		response.put("number", counter);
		
		// Increment the counter for next request.
		counter++;
		
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getRequest(int number) {
		// Get the definition of the word in the associated request if ready and remove it from the map.
		String definition = outQueue.remove(number);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("ready", definition != null);
		response.put("definition", definition);
		
		return response;
	}
}
