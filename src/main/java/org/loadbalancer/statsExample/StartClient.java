package org.loadbalancer.statsExample;

/**
 * Starts the client and request the data from the server
 */
public class StartClient {
	public static void main(String[] args) {

		Client t= new Client("localhost", 1777);
		
		t.sendStats();
		
		t.sendStats();
	
	}
	
}
