package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CLIObservableView extends Observable implements View {

	public CLI getCli() {
		return cli;
	}

	CLI cli;
	String userInput;
	
	public String getUserInput() {
		return userInput;
	}

	@Override
	public void setUserInput(String userInput) {
		this.userInput = userInput;
		setChanged();
		notifyObservers();
	}

	public CLIObservableView(CLI cli) {
		this.cli = cli;
		this.cli.setView(this);
	}

	@Override
	public String getUserCommand() {
		return userInput;
	}

	//OMER
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
	}

	//Daniel
	@Override
	public void displayFiles(String[] fileslist) {
		for(int i=0; i < fileslist.length-1; i++)
			System.out.println(fileslist[i]);
	}
}
