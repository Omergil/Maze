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
	public void displayData(int[][][] data) {
		for (int i = 0; i < data[0][0].length;i++)
		{
			for (int j = 0; j < data[0].length; j++)
			{
				for (int k = 0; k < data.length; k++)
				{
					System.out.println(data[k][j][i]);
				}
			}
		}
	}

	@Override
	public String getUserCommand() {
		return userInput;
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);
	}
}
