# Distributed Systems Project
This is my project for the 4th year distributed systems module in college. For this project I was required to use the Servlet/JSP and Java RMI frameworks to develop a remote, asynchronous dictionary lookup service.

## Project Overview
A JSP page should provide users with the ability to specify a string which will be checked against the dictionary. The HTML form information should be dispatched to a servlet that adds the client request to an in-queue and then returns a job ID to the web client. The web client should poll the web server periodically (every 10 seconds) and query if the request has been processed. Client requests in the inQueue should be periodically removed and processed (every 10 seconds). The processing of a client request will require a RMI method invocation to a remote object which implements an interface called DictionaryService. The remote object which implements DictionaryService should check if the string received exists in the dictionary, and return the dictionary definition of the string if it does exist in the dictionary, or "String not found" if it does not exist in the dictionary. Once the result of the dictionary lookup has been computed by the remote object, the returned response should be added to the outQueue on the Tomcat server and returned to the original web client when they next poll the server.

## Project Requirements
The following are the minimum requirements for this project.
+ A web client request should be placed in a message queue to await processing. Each request should be allocated a job number. The job number should be added to an inQueue (Map) along with the request string. The servlet handler should return the job number to the client which in turn should poll the server every 10 seconds for a response. When a response is received with a completed task, the result of the dictionary lookup should be displayed in the browser.
+ An interface called DictionaryService should expose a remote method with the following signature:

	```
	public String lookup(String s) throws RemoteException;
	```

	Where s is the string to lookup in the dictionary, and the string returned is either the dictionary definition of s or the text "String not found". In the DictionaryServiceImpl, before looking up the query string in the dictionary the thread should be put to sleep for a time, i.e. Thread.sleep(1000), to slow the service down and simulate a real asynchronous service.
+ A sample dictionary containing ~50 words.

Extra features or enhancements are worth 20% of the project. Examples include the following.
+ Allow clients to add/remove/modify entries in the dictionary.
+ Add multithreading functionality, so that multiple RMI clients can make queries to the RMI Dictionary Service concurrently (Thread Pools).
+ Use a real dictionary, for example, [Webster’s Dictionary](http://www.gutenberg.org/ebooks/29765).

## Extra features
+ This project uses a slightly modified version of Webster’s Dictionary.
