package presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer {

	Model model;
	View ui;
	ArrayList<String> arguments;
	HashMap<String, Command> hashMap = new HashMap<String, Command>();

	public Presenter(Model model, View ui) {
		this.model = model;
		this.ui = ui;
		setHashMap();
	}
	
	private void setHashMap() {
		hashMap.put("generate", new Generate());
	}

	@Override
	public void update(Observable observable, Object arg1) {
		if (observable == ui)
		{
			String input = ui.getUserCommand();
			arguments = splitArguments(input);
			if (hashMap.containsKey(arguments.get(0)))
			{
				hashMap.get(arguments.get(0)).doCommand();
			}
			else
			{
				wrongInput();
			}
		}
		
		if (observable == model)
		{
			ui.displayMessage((String) arg1);
		}
	}
	
	private ArrayList<String> splitArguments(String userInput)
	{
		ArrayList<String> arguments = new ArrayList<String>();
		arguments = new ArrayList<String>(Arrays.asList(userInput.split("\\s+")));
		return arguments;
	}
	
	private void wrongInput()
	{
		ui.displayMessage("Sorry, wrong command.");
	}
	
	private class Generate implements Command
	{
		@Override
		public void doCommand() {
			model.generate(arguments.get(3), Integer.parseInt(arguments.get(4)), Integer.parseInt(arguments.get(5)), Integer.parseInt(arguments.get(6)));
		}		
	}
}
