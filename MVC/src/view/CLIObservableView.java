package view;

import java.io.InputStream;
import java.io.OutputStream;
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
	}

	//Daniel
	@Override
	public void displayFiles(String[] fileslist) {
		for(int i=0; i < fileslist.length-1; i++)
			System.out.println(fileslist[i]);
	}
}
