package boot;

import java.io.FileNotFoundException;
import model.Maze3dModel;
import presenter.Presenter;
import presenter.Properties;
import view.CLI;
import view.CLIObservableView;

/**
 * Main program to run a CLI to perform operations by the user on an MVP architecture.
 */
public class Run {
	public static void main(String[] args) throws FileNotFoundException {
		//DELETE
		Properties p = new Properties();
		
		CLIObservableView ui = new CLIObservableView(new CLI(System.in, System.out));
		Maze3dModel model = new Maze3dModel();
		Presenter presenter = new Presenter(model, ui);
		ui.addObserver(presenter);
		model.addObserver(presenter);
		ui.getCli().run();
	}
}
