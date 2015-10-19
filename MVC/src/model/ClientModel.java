package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import presenter.ClientProperties;

/**
 * Model object for MVP architecture, specifically for Maze3d.
 */
public class ClientModel extends Observable implements Model {

	PrintWriter outToServer;
	ObjectInputStream inFromServer;
	Socket theServer;
	boolean alreadyClosed = false;
	boolean socketClosed = false;
	
	// DELETE THREADS???
	int numOfThreads = 20;
	ExecutorService exec = Executors.newFixedThreadPool(numOfThreads);

	public ClientModel(String host, int port) {
		try {
			theServer = new Socket(host, port);
			System.out.println("Connected to server!");
			outToServer = new PrintWriter(theServer.getOutputStream());
			inFromServer = new ObjectInputStream(theServer.getInputStream());
		} catch (UnknownHostException e) {
			System.out.println("Error connecting to server.");
			return;
		} catch (IOException e) {
			System.out.println("Error connecting to server.");
			return;
		}
		
		getFromServer();
	}

	@Override
	public void sendToServer(String userInput) {
		outToServer.println(userInput);
		outToServer.flush();
	}
	
	@Override
	public void getFromServer() {
		new Thread(new Runnable() {
			public void run() {
				try {
						Object object;
						while (!socketClosed)
						{
							try {
								object = inFromServer.readObject();
								setChanged();
								notifyObservers(object);
							} catch (ClassNotFoundException e) {
								continue;
							};
						}
				} catch (IOException e) {
					if (!socketClosed)
					{
						setChanged();
						notifyObservers("Error with connection to the server.");
					}	
				}	
			}
		}).start();
	}
	
	/**
	 * Receives a path to an XML properties file and loads its content.
	 */
	@Override
	public void loadProperties(String filePath) {
		ClientProperties properties = new ClientProperties(filePath);
		properties.load();
		if (properties.isPropertiesSet())
		{
			setChanged();
			notifyObservers(properties);
		}
		else
		{
			setChanged();
			notifyObservers("Properties file is corrupted / doesn't exist.");
		}
	}

	/**
	 * Terminates the client session.
	 */
	@Override
	public void exit() {

		try {
			if (!alreadyClosed)
			{
				socketClosed = true;
				alreadyClosed = true;
				setChanged();
				notifyObservers("Bye bye!");
				//inFromServer.close();
				outToServer.println("exit");
				outToServer.flush();
				outToServer.close();
				theServer.close();
				exec.shutdown();
			}
		} catch (IOException e) {
			e.printStackTrace();}
	}
	
	
}
