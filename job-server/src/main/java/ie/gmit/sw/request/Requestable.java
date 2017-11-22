package ie.gmit.sw.request;

/**
 * The Requestable interface should be implemented by any object that is sent to the server.
 */
public interface Requestable {
	public String getPhrase();
	public int getNumber();
}