package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import presenter.ServerProperties;
import view.MazeClientHandler;

/**
 * Main Class to run the server side.
 */
public class RunServer {
	
	public static void main(String[] args) {
		System.out.println("Server side.");
		System.out.println("type \"close server\" to shut it down.");
		ServerProperties serverProperties = new ServerProperties("ServerProperties.xml");
		serverProperties.load();
		MyTCPIPServer server = new MyTCPIPServer(serverProperties.getPort(), new MazeClientHandler(), serverProperties.getNumOfThreads());
		
		server.start();
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		try {
			while(!(in.readLine()).equals("close server"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		server.close();
	}

}
