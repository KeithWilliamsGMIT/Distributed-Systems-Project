package ie.gmit.sw.request;

/**
 * Implementation of the Requestable interface.
 */
public class Request implements Requestable {
	private String phrase;
	private int number;
	
	/**
	 * Fully parameterised constructor for the Request class.
	 * @param phrase to look up in the dictionary.
	 * @param number allocated to the request.
	 */
	public Request(String phrase, int number) {
		super();
		this.phrase = phrase;
		this.number = number;
	}
	
	// Getters and setters
	public String getPhrase() {
		return phrase;
	}
	public int getNumber() {
		return number;
	}
}
