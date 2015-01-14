package org.loadbalancer.statsExample;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ClientSocket {
	
	private String werte;
	private String host;
	private int port;
	private boolean end;
	
	public ClientSocket(String host, int port, boolean end) {
		this.end = end;
		this.host= host;
		this.port= port;
	}
	
	public String worker() throws Exception {
		//System.out.println("worker:" + Thread.currentThread());
		//Klasse die 'Callable' implementiert
		ClientHandler ch = new ClientHandler(werte);
		boolean weiter = false;
		String erg="";
		do {  //2 Durchläufe
			int j = 0;
			//call-Methode 'ch' von ClientHandler wird mit 'FutureTask' asynchron
			//abgearbeitet, das Ergebnis kann dann von der 'FutureTask' abgeholt
			//werden.
			FutureTask<String> ft = new FutureTask<String>(ch);
			Thread tft = new Thread(ft);
			tft.start();

			//pruefe ob der Thread seine Arbeit getan hat
			while (!ft.isDone()) {
				j++;  //zähle die Thread-Wechsel
				Thread.yield();  //andere Threads (AndererThread) koennen drankommen
			}
			//System.out.println("not isDone:" + j);
			
			erg= (ft.get());  //Ergebnis ausgeben
			
			if (end == false)
				break;
			//weiter = !weiter;
		} while (end);
		
		return erg;
	}
}

//Enthält die call-Methode fuer die FutureTask (entspricht run eines Threads)
class ClientHandler implements Callable<String> {
	String ip = "127.0.0.1";  //localhost
	int port = 3141;
	String werte;

	public ClientHandler(String werte) {
		this.werte = werte;
	}
	void setWerte(String s) {
		werte = s;
	}
	public String call() throws Exception {  //run the service
		//System.out.println("ClientHandler:" + Thread.currentThread());
		//verlängere kuenstlich die Bearbeitung der Anforderung, um das Wechselspiel
		//der Threads zu verdeutlichen
		Thread.sleep(2000);
		return RequestServer(werte);
	}

	//Socket oeffnen, Anforderung senden, Ergebnis empfangen, Socket schliessen
	String RequestServer(String par) throws IOException {
		String empfangeneNachricht;
		String zuSendendeNachricht;

		Socket socket = new Socket(ip,port);  //verbindet sich mit Server
		zuSendendeNachricht = par;
		//Anforderung senden
		schreibeNachricht(socket, zuSendendeNachricht);
		//Ergebnis empfangen
		empfangeneNachricht = leseNachricht(socket);
		socket.close();
		return empfangeneNachricht;
	}
	void schreibeNachricht(Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter =
				new PrintWriter(
						new OutputStreamWriter(
								socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}
	String leseNachricht(Socket socket) throws IOException {
		BufferedReader bufferedReader =
				new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()));
		char[] buffer = new char[200];
		//blockiert bis Nachricht empfangen
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200);
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}
}
