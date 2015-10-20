package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import model.Maze3dModel;
import server.MyTCPIPServer;

public class ServerCLI{
	BufferedReader in;
	PrintWriter out;
	ClientHandler view;
	String line;
	int port = 9999;
	

	/**
	 * Constructor for the ServerCLI.
	 * @param in
	 * @param out
	 */
	public ServerCLI(InputStream in, OutputStream out, int port){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(new OutputStreamWriter(out));
		if ((port >= 1) || (port <= 65535))
			this.port =port; 
	}

	public void runCLI()
	{
		System.out.println("Server side.");
		System.out.println("type \"close server\" to shut it down.");
		
		MyTCPIPServer server = new MyTCPIPServer(port, view, 10);
		server.start();
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		try{
			while(!(in.readLine()).equals("close server"));
		}catch(IOException e){
			e.printStackTrace();
		}
	
		server.close();
	
	}
	
}
