package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface that exposes the public service methods that may be invoked by a remote object.
 * All remote methods must throw a RemoteException.
 * In RMI, a class that implements a remote interface is called a Remote Object.
 */
public interface DictionaryService extends Remote {
	
	/**
	 * Find the definition of a string in the dictionary.
	 * @param s the string to lookup in the dictionary.
	 * @return either the dictionary definition of s or the text "String not found".
	 * @throws RemoteException
	 */
	public String lookup(String s) throws RemoteException;
}