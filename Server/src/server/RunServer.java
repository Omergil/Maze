package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Maze3dModel;
import presenter.Presenter;
import view.MazeClientHandler;

public class RunServer {

	public static void main(String[] args) {
		System.out.println("Server side.");
		System.out.println("type \"close server\" to shut it down.");
		Maze3dModel model = new Maze3dModel();
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
