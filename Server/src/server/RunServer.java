package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Maze3dModel;
import presenter.Presenter;
import presenter.ServerProperties;
import view.MazeClientHandler;

public class RunServer {
	
	public static void main(String[] args) {
		Maze3dModel model;
		System.out.println("Server side.");
		System.out.println("type \"close server\" to shut it down.");
		ServerProperties serverProperties = new ServerProperties("ServerProperties.xml");
		serverProperties.load();
		try {
			model = new Maze3dModel(serverProperties.getNumOfThreads());			
		} catch (Exception e) {
			model = new Maze3dModel();
		}
		// Load the mazes from zip file
		model.loadMap();
		MazeClientHandler view = new MazeClientHandler();
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		MyTCPIPServer server = new MyTCPIPServer(9999, view, 10);
		
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
