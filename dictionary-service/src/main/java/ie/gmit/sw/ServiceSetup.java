package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for creating an instance of the remote object.
 * It binds the remote object to a naming server (RMI registry) with a human-readable name.
 * When a client asks the RMI registry for an object, the registry returns an instance of the remote interface.
 * This instance is called the stub.
 */
public class ServiceSetup {
	
	/**
	 * Entry point for service application.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		Map<String, String> dictionary = parseDictionary();
		
		// Create an instance of a DictionaryService.
		DictionaryService ds = new DictionaryServiceImpl(dictionary);
		
		// Start the RMI registry on port 1099 (Default port).
		LocateRegistry.createRegistry(1099);
		
		// Bind the remote object to the registry.
		// Assign it the human-readable name "dictionaryService".
		Naming.rebind("dictionaryService", ds);
	}
	
	/*
	 * Parse the dictionary file, which is in TXT format, to a Map.
	 */
	private static Map<String, String> parseDictionary() throws Exception {
		InputStream in = ServiceSetup.class.getResourceAsStream("/dictionary.txt");
		Map<String, String> dictionary = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader (in));
		
		String line = null;
		String word = null;
		String definition = "";
		
		// Read file line by line until eof O(n)
		// Add each word to dictionary map O(1)
		while ((line = br.readLine()) != null) {
			// Check if the next line is a new word in the dictionary.
			if (line.length() > 0) {
				if (line.equals(line.toUpperCase()) && line.matches("[a-zA-Z0-9\\-\\s]+")) {
					// Put the last word and definition in the dictionary map.
					if (word != null) {
						dictionary.put(word, definition);
					}
					
					word = line;
					
					// Clear the definition if the word isn't already in the dictionary map.
					if (dictionary.get(word) == null) {
						definition = "";
					}
				} else {
					definition = definition + line;
				}
			} else {
				definition = definition + "<br/>";
			}
		}
		
		// Add the last word to the dictionary map.
		dictionary.put(word, definition);
		
		System.out.println("Number of words in dictionary: " + dictionary.size());
		
		return dictionary;
	}
}