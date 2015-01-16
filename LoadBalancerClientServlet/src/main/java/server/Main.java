package server;

import serverLoadRmi.Server;

import java.io.IOException;

public class Main {
	public static void main(final String[] args) throws IOException{

		try{
			//Start the rmi Server (The load Announcer)
			Server.main(new String[0]);
		}catch (Exception e){
			e.printStackTrace();
		}

		if (args.length > 0) {
			int port = 8080;
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("Argument" + args[0] + " must be an integer.");
				System.err.println("Defaulting to 8080");
			}
			System.out.println("Starting Jetty ..");
			new JettyEmbeddedRunner().startServer(port);
		} else {
			new JettyEmbeddedRunner().startServer(8083);
			new JettyEmbeddedRunner().startServer(8084);
		}
		System.out.println("Started Servlet/s");
	}
}