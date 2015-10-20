package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import model.Maze3dModel;
import presenter.Presenter;
import presenter.ServerProperties;
import view.MazeClientHandler;
import view.ServerCLI;

public class NewServerRun {
	
	public static void main(String[] args) {
		Maze3dModel model;
		ServerProperties serverProperties = new ServerProperties("ServerProperties.xml");
		serverProperties.load();
		try {
			model = new Maze3dModel(serverProperties.getNumOfThreads());			
		} catch (Exception e) {
			model = new Maze3dModel();
		}
		// Load the mazes from zip file - need to move
		model.loadMap();
		
		MazeClientHandler view = new MazeClientHandler();
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		ServerCLI servercli = new ServerCLI(System.in, System.out,9999);
		servercli.runCLI();	
		
		//Need to move
		model.saveMap();
		model.closeThreadPool();
	}
}