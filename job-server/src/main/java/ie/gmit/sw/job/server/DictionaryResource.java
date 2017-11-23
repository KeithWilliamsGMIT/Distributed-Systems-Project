package ie.gmit.sw.job.server;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * An interface that defines methods for handling HTTP requests relating to dictionary resources.
 */
public interface DictionaryResource {

	/**
	 * This method handles POST requests. Create a new request and queues it for processing.
	 * @param query, representing a word or phrase, to search for in the dictionary.
	 * @return JSON containing a message which can be displayed to the user and the job number for the request.
	 * @throws InterruptedException 
	 */
	@POST
	@Path("/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> postRequest(@PathParam("query") String query) throws InterruptedException;
	
	/**
	 * This method handles GET requests. Retrieve the result of the request with the given job number is processed.
	 * @param number of the request.
	 * @return JSON containing the definition of the word in the request.
	 */
	@GET
	@Path("/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getRequest(@PathParam("number") int number);

}