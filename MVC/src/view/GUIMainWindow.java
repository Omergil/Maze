package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import presenter.Properties;

/**
 * Main window to present the game.
 * <p>
 * Contains a menu on the left, and a game on the right side.<br>
 * Each part can be easily modified by using different classes.
 */
public class GUIMainWindow extends BasicWindow implements View {

	String userInput;
	GUIMazeDisplayer3d maze3dDisplay;
	HashMap<String, String> inputHashMap;
	String defaultMazeSolvingAlgorithm = "bfs";
	String defaultMazeName = "NewMaze";
	String defaultX = "10";
	String defaultY = "4";
	String defaultFloors = "5";
	
	/**
	 * Constructor to set window size.
	 * @param width
	 * @param height
	 */
	public GUIMainWindow(int width, int height) {
		super(width, height);
	}

	/**
	 * Sets all widgets in the window.
	 */
	@Override
	void initWidgets() {
		
		shell.setLayout(new GridLayout(2, false));
		setUserInput("properties Properties.xml");
		
		Button generateMaze  = new Button(shell, SWT.PUSH);
		generateMaze.setText("Generate Maze");
		generateMaze.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		generateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUIGenerateMazeDialog generateMazeDialog = new GUIGenerateMazeDialog(shell, defaultMazeName, defaultX, defaultY, defaultFloors);
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
				GUISolveMazeDialog solveMazeDialog = new GUISolveMazeDialog(shell, defaultMazeSolvingAlgorithm);
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
		displaySolution.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//COMPLETE
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button loadProperties = new Button(shell, SWT.PUSH);
		loadProperties.setText("Load Properties");
		loadProperties.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		loadProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUILoadPropertiesDialog loadPropertiesDialog = new GUILoadPropertiesDialog(shell);
				inputHashMap = loadPropertiesDialog.open();
				if (!(inputHashMap == null))
				{
					setUserInput("properties " + inputHashMap.get("filePath"));					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
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
		
		//move the character on the board
		maze3dDisplay.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if (e.keyCode == SWT.ARROW_LEFT) {
					maze3dDisplay.moveLeft();
				}
				if (e.keyCode == SWT.ARROW_RIGHT) {
					maze3dDisplay.moveRight();
				}
				if (e.keyCode == SWT.ARROW_UP) {
					maze3dDisplay.moveForwards();
				}
				if (e.keyCode == SWT.ARROW_DOWN) {
					maze3dDisplay.moveBackwards();
				}
				if (e.keyCode == SWT.PAGE_UP) {
					maze3dDisplay.moveUp();
				}
				if (e.keyCode == SWT.PAGE_DOWN) {
					maze3dDisplay.moveDown();
				}
			}
		});
	}

	/**
	 * Receives input from the presenter and acts accordingly.
	 */
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
		else if (data instanceof Properties)
		{
			Properties properties = (Properties) data;
			if (properties.isPropertiesSet())
			{
				defaultMazeSolvingAlgorithm = properties.getMazeSolvingAlgorithm().toLowerCase();	
				defaultMazeName = properties.getMazeName();
				defaultX = String.valueOf(properties.getMazeWidth());
				defaultY = String.valueOf(properties.getMazeHeight());
				defaultFloors = String.valueOf(properties.getMazeFloors());
			}
		}
	}

	/**
	 * Gets the wanted command (used mainly by the presenter).
	 */
	@Override
	public String getUserCommand() {
		return userInput;
	}

	/**
	 * Sets the userInput.
	 */
	@Override
	public void setUserInput(String userInput) {
		this.userInput = userInput;
		setChanged();
		notifyObservers();		
	}
}
