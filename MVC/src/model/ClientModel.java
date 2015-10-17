package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

/**
 * Model object for MVP architecture, specifically for Maze3d.
 */
public class ClientModel extends Observable implements Model {

	PrintWriter outToServer;
	BufferedReader inFromServer;
	Socket theServer;
	
	int numOfThreads = 20;
	HashMap<String, Maze3d> mazeStore = new HashMap<String, Maze3d>();
	HashMap<String, Solution> solutionsStore = new HashMap<String, Solution>();
	ExecutorService exec = Executors.newFixedThreadPool(numOfThreads);
	Object[][] solArray;

	public ClientModel(String host, int port) {
		try {
			theServer = new Socket(host, port);
			System.out.println("Connected to server!");
			outToServer = new PrintWriter(theServer.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//DELETE???
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
					String line;
					
					while(!(line = inFromServer.readLine()).endsWith("Bye bye!"))
					{
						setChanged();
						notifyObservers(line);
					}
					inFromServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				setChanged();
				notifyObservers(123);				
			}
		}).start();
	}
}
