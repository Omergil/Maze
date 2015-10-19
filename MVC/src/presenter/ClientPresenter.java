package presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;

public class ClientPresenter implements Observer {

	Model model;
	View ui;
	ArrayList<String> arguments;
	HashMap<String, Command> hashMap = new HashMap<String, Command>();

	/**
	 * Constructor for presenter layer in MVP.
	 * @param model
	 * @param ui
	 */
	public ClientPresenter(Model model, View ui) {
		this.model = model;
		this.ui = ui;
		setHashMap();
	}

	public void setHashMap() {
		hashMap.put("properties", new LoadClientProperties());
		hashMap.put("properties", new LoadProperties());
		hashMap.put("exit", new Exit());
	}

	/**
	 * Method used when an observable notifies the observer (the presenter).
	 * <p>
	 * If the notifier is the view layer - perform an operation using the model layer.<br>
	 * If the notifier is the model layer - display the results for the client in the view layer.
	 */
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
				model.sendToServer(ui.getUserCommand());
			}
		}
		
		else if (observable == model)
		{
			
			ui.displayData(arg1);
		}
	}
	
	/**
	 * Loads the client's properties from the XML file.
	 */
	public class LoadClientProperties implements Command{
		@Override
		public void doCommand() {
			if (arguments.size() >= 2)
			{
				String filePath = arguments.get(1);
				for (int i = 2; i < arguments.size(); i++)
				{
					filePath = filePath + " " + arguments.get(i);
				}
				model.loadProperties(filePath);				
			}
			else
			{
				ui.displayData("Error for loading client properties.");
			}
		}
	}
	
	/**
	 * Split arguments for a command given by the client.<br>
	 * The arguments are returned in array list of Strings.
	 * @param userInput
	 * @return ArrayList<String> containing the command arguments.
	 */
	private ArrayList<String> splitArguments(String userInput)
	{
		ArrayList<String> arguments = new ArrayList<String>();
		arguments = new ArrayList<String>(Arrays.asList(userInput.split("\\s+")));
		return arguments;
	}
	
	/**
	 * Loads the server's properties from the XML file.
	 */
	public class LoadProperties implements Command{

		@Override
		public void doCommand() {
			if (arguments.size() >= 2)
			{
				String filePath = arguments.get(1);
				for (int i = 2; i < arguments.size(); i++)
				{
					filePath = filePath + " " + arguments.get(i);
				}
				model.loadProperties(filePath);				
			}
			else
			{
				ui.displayData("Error occured for Load Properties funcionality.");
			}
		}
	}
	
	/**
	 * Exit from client session.
	 */
	public class Exit implements Command{
		@Override
		public void doCommand() {
			if (arguments.size() == 1)
			{
				model.exit();			
			}
			else
			{
				ui.displayData("Error occured for exit.");
			}
		}
	}
	
	
	
	
	
}
