package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * View layer of MVP architecture.
 * <p>
 * In charge of presenting data to the client.<br>
 * Object adapter for CLI and Observable.
 */
public class CLIObservableView extends Observable implements View {

	/**
	 * Getter for the CLI object.
	 * @return CLI object.
	 */
	public CLI getCli() {
		return cli;
	}

	CLI cli;
	String userInput;
	
	/**
	 * Getter for the user input.
	 * @return String (user input).
	 */
	public String getUserInput() {
		return userInput;
	}

	/**
	 * Sets the userInput String.
	 */
	@Override
	public void setUserInput(String userInput) {
		this.userInput = userInput;
		setChanged();
		notifyObservers();
	}

	/**
	 * Constructor for the CLIObservableView object.
	 * @param cli
	 */
	public CLIObservableView(CLI cli) {
		this.cli = cli;
		this.cli.setView(this);
	}

	@Override
	public String getUserCommand() {
		return userInput;
	}

	/**
	 * Display data received from the presenter layer.
	 * <p>
	 * The data is printed to the screen by its type - int[][][], int[][], String, etc.
	 */
	@Override
	public void displayData(Object data) {
		if (data instanceof String)
		{
			System.out.println(data);			
		}
		else if (data instanceof int[][][])
		{
			int[][][] maze = (int[][][]) data;
			for (int floors = 0; floors < maze[0][0].length; floors++)
			{
				for (int height = maze.length - 1; height >= 0; height--)
				{
					for (int width = 0; width < maze[0].length; width++)
					{
						System.out.print(" " + maze[height][width][floors] + " ");
					}
					System.out.println();
				}
				System.out.println("\n");
			}
		}
		else if (data instanceof int[][])
		{
			int[][] maze2d = (int[][]) data;
			for (int i = 0; i < maze2d.length; i++)
			{
				for (int j = 0; j < maze2d[0].length; j++)
				{
					System.out.print(" " + maze2d[i][j] + " ");
				}
				System.out.println("\n");
			}
		}
		else if (data instanceof List)
		{
			ArrayList<String> states = (ArrayList<String>) data;
			for (String state : states)
			{
				System.out.println(state);
			}
		}
		else if (data instanceof String[])
		{
			String[] strings = (String[]) data;
			for(int i=0; i < strings.length-1; i++)
				System.out.println(strings[i]);
		}
	}
}