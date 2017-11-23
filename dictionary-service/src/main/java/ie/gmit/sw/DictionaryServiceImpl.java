package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * DictionaryServiceImpl is a Remote Object.
 * Extending UnicastRemoteObject allows this remote object to communicate with the server-side skeleton.
 * The server-side skeleton is the responsible for unmarshalling client requests on the server and marshalling responses.
 * The remote object is used for a remote method invocation.
 */
public class DictionaryServiceImpl extends UnicastRemoteObject implements DictionaryService {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor for the DictionaryServiceImpl class.
	 * @throws RemoteException
	 */
	public DictionaryServiceImpl() throws RemoteException { }
	
	/**
	 * {@inheritDoc}
	 */
	public String lookup(String s) throws RemoteException {
		String definition = "String not found";
		
		return definition;
	}
}