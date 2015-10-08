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
	HashMap<String, String> inputHashMap;
	
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
				inputHashMap = generateMazeDialog.open();
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
		maze3dDisplay.setMaze(new MyMaze3dGenerator(10, 4, 3).generate()); //DELETE!!!
		maze3dDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 11));
		
		
		Button displayMaze = new Button(shell, SWT.PUSH);
		displayMaze.setText("Display a maze");
		displayMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		displayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GUIDisplayMazeDialog displayMazeDialog = new GUIDisplayMazeDialog(shell);
				inputHashMap = displayMazeDialog.open();
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
		saveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUISaveMazeDialog saveMazeDialog = new GUISaveMazeDialog(shell);
				inputHashMap = saveMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("save maze " + inputHashMap.get("mazeName") + " " + inputHashMap.get("filePath"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button loadMaze = new Button(shell, SWT.PUSH);
		loadMaze.setText("Load Maze");
		loadMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		loadMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUILoadMazeDialog loadMazeDialog = new GUILoadMazeDialog(shell);
				inputHashMap = loadMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("load maze " + inputHashMap.get("filePath") + " " + inputHashMap.get("mazeName"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button mazeSize = new Button(shell, SWT.PUSH);
		mazeSize.setText("Maze Size");
		mazeSize.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		mazeSize.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUIMazeSizeDialog mazeSizeDialog = new GUIMazeSizeDialog(shell);
				inputHashMap = mazeSizeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("maze size " + inputHashMap.get("mazeName"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button fileSize = new Button(shell, SWT.PUSH);
		fileSize.setText("File Size");
		fileSize.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		fileSize.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e){
				GUIFileSizeDialog fileSizeDialog = new GUIFileSizeDialog(shell);
				inputHashMap = fileSizeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("file size " + inputHashMap.get("filePath"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button displayHint = new Button(shell, SWT.PUSH);
		displayHint.setText("Get a Hint!");
		displayHint.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		
		Button solveMaze = new Button(shell, SWT.PUSH);
		solveMaze.setText("Solve Maze");
		solveMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		solveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUISolveMazeDialog solveMazeDialog = new GUISolveMazeDialog(shell);
				inputHashMap = solveMazeDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("solve " + inputHashMap.get("mazeName") + " " + inputHashMap.get("algorithm"));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button displaySolution = new Button(shell, SWT.PUSH);
		displaySolution.setText("Display Solution");
		displaySolution.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));

		Button setProperties = new Button(shell, SWT.PUSH);
		setProperties.setText("Set Properties");
		setProperties.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		
		Button exit = new Button(shell, SWT.PUSH);
		exit.setText("Exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		exit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setUserInput("exit");
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	@Override
	public void displayData(Object data) {
		if (data instanceof Maze3d)
		{
			Maze3d maze = (Maze3d) data;
			maze3dDisplay.setMaze(maze);
			maze3dDisplay.redraw();
		}
		else if (data instanceof String)
		{
			String message = (String) data;
			display.syncExec(new Runnable() {
				public void run() {
					new GUIMessageBox(shell, message);
				}
			});
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
