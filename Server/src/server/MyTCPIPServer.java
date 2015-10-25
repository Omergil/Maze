package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import model.Database;
import model.Maze3dModel;
import presenter.Presenter;
import view.ClientHandler;
import view.MazeClientHandler;

/**
 * Configures a TCP/IP server to manage multiple clients at a time.
 */
public class MyTCPIPServer {
	int port;
	ServerSocket server;
	ClientHandler clientHandler;
	int numOfClients;
	ExecutorService threadPool;
	volatile boolean stop;
	Thread mainServerThread;
	int clientsHandled = 0;
	Database database;
	Maze3dModel model;
	
	/**
	 * Constructor.
	 * <p>
	 * - Sets the port to use on the server side, number of clients, and clientHandler.<br>
	 * - Creates a new database.<br>
	 * - Loads all existing mazes and solutions from cache mode.
	 * @param port
	 * @param clientHandler
	 * @param numOfClients
	 */
	public MyTCPIPServer(int port, ClientHandler clientHandler, int numOfClients)
	{
		this.port = port;
		this.clientHandler = clientHandler;
		this.numOfClients = numOfClients;
		database = new Database();
		model = new Maze3dModel(database);
		model.loadMap();
	}
	
	/**
	 * Starts the new server thread.
	 */
	public void start()
	{
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(15*1000);
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
										int numOfClient = clientsHandled;
										System.out.println("Handling client " + numOfClient);
										MazeClientHandler view = new MazeClientHandler();
										Maze3dModel model = new Maze3dModel(database);
										Presenter presenter = new Presenter(model, view);
										model.addObserver(presenter);
										view.addObserver(presenter);
										view.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										model.closeThreadPool();
										someClient.close();
										System.out.println("Done handling client " + numOfClient);
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
	
	/**
	 * Closes the server thread.
	 * <p>
	 * Saves all mazes and solutions to cache as a zip file.<br>
	 * Waiting for all current clients to finish their job.
	 */
	public void close()
	{
		model.saveMap();
		stop = true;
		// do not execute jobs in queue, continue to execute running threads
		System.out.println("Shutting down");
		threadPool.shutdown();
		// wait 10 seconds over and over again until all running jobs have finished
		boolean allTasksCompleted=false;
		try {
			System.out.println("Waiting for active clients to shut down...");
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
