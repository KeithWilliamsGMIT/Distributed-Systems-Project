package ie.gmit.sw.job.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Handle HTTP requests sent to the /dictionary endpoint.
 */
@Path("/dictionary")
public class DictionaryResource {
	/**
	 * This method handles POST requests. Creates a new request and queues it for processing.
	 * @param query, representing a word or phrase, to search for in the dictionary.
	 * @return a message to acknowledge that the request was created and is being processed.
	 */
	@POST
	@Path("/{query}")
	@Produces(MediaType.TEXT_PLAIN)
	public String postRequest(@PathParam("query") String query) {
		// Assign job number
		
		// Add to inQueue
		
		return String.format("Searching for '%s'...", query);
	}
}
