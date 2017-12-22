package ie.gmit.sw.job.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import ie.gmit.sw.request.Request;
import ie.gmit.sw.request.RequestType;
import ie.gmit.sw.request.Requestable;

/**
 * Handle HTTP requests sent to the /dictionary endpoint.
 */
@Path("/dictionary")
@Singleton
public class DictionaryResourceImpl implements DictionaryResource {
	/*
	 * Number of threads to add to the thread pool.
	 */
	private static final int NUMBER_OF_THREADS = 10;
	
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
	
	/*
	 * A fixed sized thread pool for processing client requests.
	 */
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/**
	 * Constructor to create threads for processing requests.
	 */
	public DictionaryResourceImpl() {
		// Populate the thread pool with workers.
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			Runnable worker = new RmiClientThread(inQueue, outQueue);
			executor.execute(worker);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getLookupRequest(String query) throws InterruptedException {
		// Add request to the inQueue.
		Requestable<String> request = new Request<String>(RequestType.Lookup, query, counter);
		inQueue.put(request);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", String.format("Searching for '%s'...", query));
		response.put("number", counter);
		
		// Increment the counter for next request.
		counter++;
		
		return response;
	}
	
	@Override
	public Map<String, Object> postAddRequest(String word, String definition) throws InterruptedException {
		// Add request to the inQueue.
		Map<String, String> data = new HashMap<String, String>();
		data.put(word, definition);
		Requestable<Map<String, String>> request = new Request<Map<String, String>>(RequestType.Add, data, counter);
		inQueue.put(request);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", String.format("Adding '%s'...", word));
		response.put("number", counter);
		
		// Increment the counter for next request.
		counter++;
		
		return response;
	}

	@Override
	public Map<String, Object> deleteRemoveRequest(String word) throws InterruptedException {
		// Add request to the inQueue.
		Requestable<String> request = new Request<String>(RequestType.Remove, word, counter);
		inQueue.put(request);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", String.format("Removing '%s'...", word));
		response.put("number", counter);
		
		// Increment the counter for next request.
		counter++;
		
		return response;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getPollRequest(int number) {
		// Get the definition of the word in the associated request if ready and remove it from the map.
		String definition = outQueue.remove(number);
		
		// Create the response object.
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("ready", definition != null);
		response.put("details", definition);
		
		return response;
	}
}
