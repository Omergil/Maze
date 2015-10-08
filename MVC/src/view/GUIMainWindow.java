package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;

public class GUIMainWindow extends BasicWindow implements View {

	String userInput;
	GUIMazeDisplayer3d maze3dDisplay;
	
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
				GUIGenerateMazeDialog generateMazeDialog = new GUIGenerateMazeDialog(shell);
				HashMap<String, String> inputHashMap = generateMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("generate 3d maze " + inputHashMap.get("mazeName") + " " + inputHashMap.get("x") +
							" " + inputHashMap.get("y") + " " + inputHashMap.get("floors"));					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		maze3dDisplay = new GUIMazeDisplayer3d(shell, SWT.BORDER);
		maze3dDisplay.setMaze(new MyMaze3dGenerator(20, 10, 2).generate()); //delete!!!
		maze3dDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8));
		
		
		Button displayMaze = new Button(shell, SWT.PUSH);
		displayMaze.setText("Display a maze");
		displayMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		displayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GUIDisplayMazeDialog displayMazeDialog = new GUIDisplayMazeDialog(shell);
				HashMap<String, String> inputHashMap = displayMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("display " + inputHashMap.get("mazeName"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
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

	@Override
	public void displayData(Object data) {
		if (data instanceof Maze3d)
		{
			Maze3d maze = (Maze3d) data;
			maze3dDisplay.setMaze(maze);
			//lmaze3dDisplay.paint
			//maze3dDisplay.setMazeChanged(true);
		}
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
