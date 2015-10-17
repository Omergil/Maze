package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.ws.handler.soap.SOAPMessageContext;

import view.ClientHandler;

public class MyTCPIPServer {
	int port;
	ServerSocket server;
	ClientHandler clientHandler;
	int numOfClients;
	ExecutorService threadPool;
	volatile boolean stop;//FALSE??
	Thread mainServerThread;
	int clientsHandled = 0;
	
	public MyTCPIPServer(int port, ClientHandler clientHandler, int numOfClients)
	{
		this.port = port;
		this.clientHandler = clientHandler;
		this.numOfClients = numOfClients;
	}
	
	public void start()
	{
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(30*1000);
			threadPool = Executors.newFixedThreadPool(numOfClients);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mainServerThread = new Thread(new Runnable() {
			public void run() {
				while(!stop)
				{
					try {
						final Socket someClient = server.accept();
						if(someClient!=null)
						{
							threadPool.execute(new Runnable() {
								public void run() {
									try {
										clientsHandled++;
										System.out.println("Handling client " + clientsHandled);
										clientHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
										System.out.println("Done handling client " + clientsHandled);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}
					} catch (SocketTimeoutException e) {
						System.out.println("No client connected...");
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Done accepting new clients.");
			} // End of the mainServerThread task
		});
		
		mainServerThread.start();
	}
	
	public void close()
	{
		stop = true;
		// do not execute jobs in queue, continue to execute running threads
		System.out.println("shutting down");
		threadPool.shutdown();
		// wait 10 seconds over and over again until all running jobs have finished
		boolean allTasksCompleted=false;
		try {
			while(!(allTasksCompleted=threadPool.awaitTermination(10, TimeUnit.SECONDS)));
			
			System.out.println("All the tasks have finished.");

			mainServerThread.join();
			System.out.println("Main server thread is done.");
			
			server.close();
			System.out.println("Server is safely closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
