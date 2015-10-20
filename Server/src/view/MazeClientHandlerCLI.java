package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import server.MyTCPIPServer;

public class MazeClientHandlerCLI extends MazeClientHandler{
	MyTCPIPServer server;
	BufferedReader in;
	PrintWriter out;
	String line;
	int port = 9999;
	int numOfClients = 10;

	/**
	 * Constructor for the ServerCLI.
	 * @param in
	 * @param out
	 */
	public MazeClientHandlerCLI(InputStream in, OutputStream out, int port, int numOfClients){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(new OutputStreamWriter(out));
		if ((port >= 1) && (port <= 65535))
			this.port = port; 
		if (numOfClients > 0)
		{
			this.numOfClients = numOfClients;
		}
		server = new MyTCPIPServer(port, this, numOfClients);
	}

	public void runCLI()
	{
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Server side.");
				System.out.println("type \"close server\" to shut it down.");
				
				server.start();
				
				BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
				try{
					while(!(in.readLine()).equals("close server"));
				}catch(IOException e){
					e.printStackTrace();
				}
			
				server.close();
				setUserInput("savemap");
				setUserInput("closeThreadPool");
			}
		}).start();
	}
}