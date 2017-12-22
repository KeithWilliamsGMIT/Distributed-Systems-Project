package ie.gmit.sw.job.server;

import java.util.Map;

import javax.ws.rs.DELETE;
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
	 * This method handles GET requests. Create a new request and queues it for processing.
	 * @param query, representing a word or phrase, to search for in the dictionary.
	 * @return JSON containing a message which can be displayed to the user and the job number for the request.
	 * @throws InterruptedException 
	 */
	@GET
	@Path("/lookup/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getLookupRequest(@PathParam("query") String query) throws InterruptedException;
	
	/**
	 * This method handles POST requests. Create a new request and queues it for processing.
	 * @param word, or phrase, to add to the dictionary.
	 * @param definition to add to the dictionary.
	 * @return JSON containing a message which can be displayed to the user and the job number for the request.
	 * @throws InterruptedException 
	 */
	@POST
	@Path("/add/{word}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> postAddRequest(@PathParam("word") String word, String definition) throws InterruptedException;
	
	/**
	 * This method handles DELETE requests. Create a new request and queues it for processing.
	 * @param word, or phrase, to remove from the dictionary.
	 * @return JSON containing a message which can be displayed to the user and the job number for the request.
	 * @throws InterruptedException 
	 */
	@DELETE
	@Path("/remove/{word}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> deleteRemoveRequest(@PathParam("word") String word) throws InterruptedException;
	
	/**
	 * This method handles GET requests. Retrieve the result of the request with the given job number is processed.
	 * @param number of the request.
	 * @return JSON containing the definition of the word in the request.
	 */
	@GET
	@Path("/poll/{number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getPollRequest(@PathParam("number") int number);

}