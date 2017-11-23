package ie.gmit.sw.job.server;

import java.rmi.Naming;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.DictionaryService;
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
	private BlockingQueue<Requestable> queue;
	
	/*
	 * If true, the thread will keep checking for new requests in the inQueue.
	 */
	private boolean keepRunning = true;
	
	/**
	 * Fully parameterised constructor for the RmiClientThread class.
	 * @param queue containing all the requests that must be processed.
	 */
	public RmiClientThread(BlockingQueue<Requestable> queue) {
		this.queue = queue;
	}
	
	/**
	 * The run() method is executed on its own thread.
	 * It will keep taking requests off the blocking queue.
	 * If the queue is empty it will block and wait for a new request to be added.
	 */
	public void run() {
		while (keepRunning) {
			try {
				// Blocking call to take the next request from the queue.
				Requestable request = queue.take();
				
				// Make request to remote dictionary service using RMI.
				// The registry is running on localhost and listening on port 1099.
				// Get an instance of the DictionaryService object that is bound to the RMI registry.
				// It has the name "dictionaryService".
				DictionaryService ds = (DictionaryService) Naming.lookup("rmi://127.0.0.1:1099/dictionaryService");
				
				// Make the remote method invocation.
				// This results in a String being transferred to us over the network. 
				String definition = ds.lookup(request.getPhrase());
				
				// Add result to outQueue.
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}