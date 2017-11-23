package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * This class is responsible for creating an instance of the remote object.
 * It binds the remote object to a naming server (RMI registry) with a human-readable name.
 * When a client asks the RMI registry for an object, the registry returns an instance of the remote interface.
 * This instance is called the stub.
 */
public class DictionaryServer {
	public static void main(String[] args) throws Exception{
		// Create an instance of a DictionaryService.
		DictionaryService ds = new DictionaryServiceImpl();
		
		// Start the RMI registry on port 1099 (Default port).
		LocateRegistry.createRegistry(1099);
		
		// Bind the remote object to the registry.
		// Assign it the human-readable name "dictionaryService".
		Naming.rebind("dictionaryService", ds);
	}
}