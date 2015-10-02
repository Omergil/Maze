package boot;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import model.Maze3dModel;
import presenter.Command;
import presenter.Presenter;
import view.CLI;
import view.CLIObservableView;

public class Run {//test!!2!!!

	public static void main(String[] args) throws InterruptedException {
		CLIObservableView ui = new CLIObservableView(new CLI(System.in, System.out));
		Maze3dModel model = new Maze3dModel();
		Presenter presenter = new Presenter(model, ui);
		ui.addObserver(presenter);
		model.addObserver(presenter);
		ui.getCli().run();
	}
}
