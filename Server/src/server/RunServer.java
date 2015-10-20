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
import view.MazeClientHandlerCLI;

public class RunServer {
	
	public static void main(String[] args) {
		Maze3dModel model;
		ServerProperties serverProperties = new ServerProperties("ServerProperties.xml");
		serverProperties.load();
		model = new Maze3dModel();
		
		// Load the mazes from ZIP file - need to move
		model.loadMap();
		
		MazeClientHandlerCLI view = new MazeClientHandlerCLI(System.in, System.out, serverProperties.getPort(), serverProperties.getNumOfThreads());
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		view.runCLI();
	}
}