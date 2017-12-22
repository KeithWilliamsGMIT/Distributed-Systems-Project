package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * DictionaryServiceImpl is a Remote Object.
 * Extending UnicastRemoteObject allows this remote object to communicate with the server-side skeleton.
 * The server-side skeleton is the responsible for unmarshalling client requests on the server and marshalling responses.
 * The remote object is used for a remote method invocation.
 */
public class DictionaryServiceImpl extends UnicastRemoteObject implements DictionaryService {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Use a HashMap to store the dictionary.
	 * The words are used as keys and the definitions are values.
	 * Looking up definitions using a key is an O(1) operation.
	 * Adding words to the dictionary is an O(1) operation.
	 */
	private Map<String, String> dictionary = new HashMap<String, String>();
	
	/**
	 * Default constructor for the DictionaryServiceImpl class.
	 * @param dictionary with words as keys and definitions as values.
	 * @throws RemoteException
	 */
	public DictionaryServiceImpl(Map<String, String> dictionary) throws RemoteException {
		this.dictionary = dictionary;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String lookup(String s) throws RemoteException {
		/* 
		 * Before looking up the query string in the dictionary
		 * the thread should be put to sleep for a time to slow the
		 * service down and simulate a real asynchronous service.
		 */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Convert string to uppercase as all the keys in the dictionary are uppercase.
		s = s.toUpperCase();
		
		// Get the defintion from the dictionary map.
		String definition = dictionary.get(s);
		
		// If the word is not in the dictionary return a default message.
		if (definition == null) {
			definition = "String not found";
		}
		
		return definition;
	}
}