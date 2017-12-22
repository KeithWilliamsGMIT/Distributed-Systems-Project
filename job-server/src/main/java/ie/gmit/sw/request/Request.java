package ie.gmit.sw.request;

/**
 * Implementation of the Requestable interface.
 */
public class Request<T> implements Requestable {
	private RequestType type;
	private T data;
	private int number;
	
	/**
	 * Fully parameterised constructor for the Request class.
	 * @param type of request.
	 * @param data for querying the dictionary.
	 * @param number allocated to the request.
	 */
	public Request(RequestType type, T data, int number) {
		super();
		this.type = type;
		this.data = data;
		this.number = number;
	}
	
	// Getters and setters
	public T getData() {
		return data;
	}
	
	public int getNumber() {
		return number;
	}
	
	public RequestType getType() {
		return type;
	}
}
