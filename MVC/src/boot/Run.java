package boot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import model.ClientModel;
import presenter.ClientPresenter;
import presenter.ClientProperties;
import view.CLI;
import view.CLIObservableView;
import view.GUIMainWindow;

/**
 * Main program to run a CLI to perform operations by the user on an MVP architecture.
 */
public class Run {
	public static void main(String[] args) throws FileNotFoundException {
		
		ClientProperties clientProperties = new ClientProperties("ClientProperties.xml");
		clientProperties.load();
		ClientModel model = new ClientModel("localhost", 9999);

		if (clientProperties.getView().equals("CLI"))
		{
			CLIObservableView ui = new CLIObservableView(new CLI(System.in, System.out));
			ClientPresenter presenter = new ClientPresenter(model, ui);
			ui.addObserver(presenter);
			model.addObserver(presenter);
			ui.getCli().run();
		}
		else if (clientProperties.getView().equals("GUI"))
		{
			GUIMainWindow gui = new GUIMainWindow(700, 600);
			ClientPresenter presenter = new ClientPresenter(model, gui);
			gui.addObserver(presenter);
			model.addObserver(presenter);
			gui.run();			
		}
		
	}
}