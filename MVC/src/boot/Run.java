package boot;

import java.io.File;
import java.io.FileNotFoundException;
import model.Maze3dModel;
import presenter.Presenter;
import presenter.Presenter.SaveMap;
import presenter.Properties;
import view.CLI;
import view.CLIObservableView;
import view.GUIMainWindow;

/**
 * Main program to run a CLI to perform operations by the user on an MVP architecture.
 */
public class Run {
	public static void main(String[] args) throws FileNotFoundException {
		
		Properties properties = new Properties("Properties.xml");
		properties.load();
		Maze3dModel model = new Maze3dModel();
		model.setNumOfThreads(properties.getNumOfThreads());
		
		//Check if the solution maze zip file exist
		model.loadMap();

		if (properties.getView().equals("CLI"))
		{
			CLIObservableView ui = new CLIObservableView(new CLI(System.in, System.out));
			Presenter presenter = new Presenter(model, ui);
			ui.addObserver(presenter);
			model.addObserver(presenter);
			ui.getCli().run();
		}
		else if (properties.getView().equals("GUI"))
		{
			GUIMainWindow gui = new GUIMainWindow(700, 600);
			Presenter presenter = new Presenter(model, gui);
			gui.addObserver(presenter);
			model.addObserver(presenter);
			gui.run();			
		}
		
	}
}