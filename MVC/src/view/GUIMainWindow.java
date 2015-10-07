package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.MyMaze3dGenerator;

public class GUIMainWindow extends BasicWindow implements View {

	String userInput;
	
	public GUIMainWindow(int width, int height) {
		super(width, height);
	}

	@Override
	void initWidgets() {

		shell.setLayout(new GridLayout(2, false));
		
		Button generateMaze  = new Button(shell, SWT.PUSH);
		generateMaze.setText("Generate Maze");
		generateMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		generateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUIGenerateMazeWindow mazeWindow = new GUIGenerateMazeWindow(shell);
				HashMap<String, String> s = mazeWindow.open();
				
				setUserInput("generate 3d maze " + s.get("mazeName") + " 4 3 2");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		//Text t = new Text(shell, SWT.MULTI | SWT.BORDER);
		//t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8));

		GUIMazeDisplayer3d g = new GUIMazeDisplayer3d(shell, SWT.BORDER);
		g.setMaze(new MyMaze3dGenerator(20, 10, 2).generate());
		g.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8));
		
		Button displayMaze = new Button(shell, SWT.PUSH);
		displayMaze.setText("Display a maze");
		displayMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		
		Button saveMaze = new Button(shell, SWT.PUSH);
		saveMaze.setText("Save Maze");
		saveMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button loadMaze = new Button(shell, SWT.PUSH);
		loadMaze.setText("Load Maze");
		loadMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button mazeSize = new Button(shell, SWT.PUSH);
		mazeSize.setText("Maze Size");
		mazeSize.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button fileSize = new Button(shell, SWT.PUSH);
		fileSize.setText("File Size");
		fileSize.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button solveMaze = new Button(shell, SWT.PUSH);
		solveMaze.setText("Solve Maze");
		solveMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button displaySolution = new Button(shell, SWT.PUSH);
		displaySolution.setText("Display Solution");
		displaySolution.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		
	}

	//DELETE??
	@Override
	public void displayData(Object data) {
		System.out.println(data);
	}

	@Override
	public String getUserCommand() {
		return userInput;
	}

	@Override
	public void setUserInput(String userInput) {
		this.userInput = userInput;
		setChanged();
		notifyObservers();		
	}

}
