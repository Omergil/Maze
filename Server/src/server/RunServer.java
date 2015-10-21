package server;

import model.Maze3dModel;
import presenter.Presenter;
import presenter.ServerProperties;
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