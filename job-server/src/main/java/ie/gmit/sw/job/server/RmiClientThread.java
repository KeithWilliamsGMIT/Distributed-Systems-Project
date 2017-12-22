package ie.gmit.sw.job.server;

import java.rmi.Naming;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import ie.gmit.sw.DictionaryService;
import ie.gmit.sw.request.RequestType;
import ie.gmit.sw.request.Requestable;

/**
 * The RmiClientThread class is used by the server to process requests.
 * Each client thread has a handle on the blocking queue of requests.
 * When a request is made it is added to this queue (Producer).
 * These requests are then taken from the blocking queue and processed (Consumer).
 * To process these requests they are used to make a lookup to a remote dictionary service using RMI
 */
public class RmiClientThread implements Runnable {
	/*
	 * The blocking queue used to queue requests that still need to be processed (inQueue).
	 */
	private BlockingQueue<Requestable> inQueue;
	
	/*
	 * The map used to store requests that have been processed.
	 */
	private Map<Integer, String> outQueue = new ConcurrentHashMap<Integer, String>();
	
	/*
	 * If true, the thread will keep checking for new requests in the inQueue.
	 */
	private boolean keepRunning = true;
	
	/**
	 * Fully parameterised constructor for the RmiClientThread class.
	 * @param queue containing all the requests that must be processed.
	 */
	public RmiClientThread(BlockingQueue<Requestable> inQueue, Map<Integer, String> outQueue) {
		this.inQueue = inQueue;
		this.outQueue = outQueue;
	}
	
	/**
	 * The run() method is executed on its own thread.
	 * It will keep taking requests off the blocking queue.
	 * If the queue is empty it will block and wait for a new request to be added.
	 */
	public void run() {
		while (keepRunning) {
			try {
				// Blocking call to take the next request from the inQueue.
				Requestable request = inQueue.take();
				
				// Make request to remote dictionary service using RMI.
				// The registry is running on localhost and listening on port 1099.
				// Get an instance of the DictionaryService object that is bound to the RMI registry.
				// It has the name "dictionaryService".
				DictionaryService ds = (DictionaryService) Naming.lookup("rmi://127.0.0.1:1099/dictionaryService");
				
				String details = "";
				
				// Make the remote method invocation.
				// This results in a String being transferred to us over the network. 
				if (request.getType().equals(RequestType.Lookup)) {
					details = ds.lookup((String) request.getData());
				} else if (request.getType().equals(RequestType.Add)) {
					Map<String, String> map = (Map<String, String>) request.getData();
					
					for (String word : map.keySet()) {
						details = ds.add(word, map.get(word));
					}
				} else {
					details = ds.remove((String) request.getData());
				}
				
				// Add result to the outQueue.
				outQueue.put(request.getNumber(), details);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}