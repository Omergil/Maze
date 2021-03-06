package presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.ClientHandler;
import view.View;

public class Presenter implements Observer {

	Model model;
	View view;
	ArrayList<String> arguments;
	HashMap<String, Command> hashMap = new HashMap<String, Command>();

	/**
	 * Constructor for presenter layer in MVP.
	 * @param model
	 * @param view
	 */
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		setHashMap();
	}
	
	/**
	 * HashMap for all available commands in the program.
	 */
	private void setHashMap() {
		hashMap.put("generate", new Generate());
		hashMap.put("help", new Help());
		hashMap.put("display", new Display());
		hashMap.put("save", new SaveMaze());
		hashMap.put("dir", new Dir());
		hashMap.put("maze", new MazeSize());
		hashMap.put("load", new LoadMaze());
		hashMap.put("file", new FileSize());
		hashMap.put("solve", new Solve());
		hashMap.put("savemap", new SaveMap());
		hashMap.put("loadmap", new LoadMap());
		hashMap.put("closeThreadPool", new CloseThreadPool());
	}

	/**
	 * Method used when an observable notifies the observer (the presenter).
	 * <p>
	 * If the notifier is the view layer - perform an operation using the model layer.<br>
	 * If the notifier is the model layer - display the results for the client in the view layer.
	 */
	@Override
	public void update(Observable observable, Object arg1) {
		if (observable == view)
		{
			String input = view.getUserCommand();
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
		
		else if (observable == model)
		{
			view.displayData(arg1);
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
	 * Operation used for failing operations, used to display an error message to the client.
	 */
	private void wrongInput()
	{
		view.displayData("Sorry, wrong command.");
	}
	
	/**
	 * Checks if a String is numeric.
	 * @param string
	 * @return True if a String is numeric.
	 */
	private boolean isNumeric(String string)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(string);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
		wrongInput();
	    return false;  
	  }  
	  return true;  
	}
	
	/**
	 * Display all possible commands and their syntax.
	 */
	private class Help implements Command
	{
		@Override
		public void doCommand() {
			view.displayData("Available commands:\n" +
					"dir <path>\n" +
					"generate 3d maze <maze_name> <width> <height> <floors>\n" +
					"display <maze_name>\n" +
					"display cross section by <X\\Y\\Z> <index> for <maze_name>\n" +
					"save maze <maze_name> <file_name>\n" +
					"load maze <file_name> <maze_name>\n" +
					"maze size <maze_name>\n" +
					"file size <file_name>\n" +
					"solve <name> <BFS\\Manhattan\\Air>\n" +
					"display solution <maze_name>\n" +
					"exit");
		}
	}
	
	/**
	 * Runs the generate operation in the model layer.
	 */
	private class Generate implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("3d")) && (arguments.get(2).equals("maze")) &&
					(arguments.size() == 7) && (isNumeric(arguments.get(4))) && (isNumeric(arguments.get(5))) &&
					(isNumeric(arguments.get(6))))
			{
				model.generate(arguments.get(3), Integer.parseInt(arguments.get(4)), Integer.parseInt(arguments.get(5)), Integer.parseInt(arguments.get(6)));	
			}
			else
			{
				wrongInput();
			}
		}		
	}

	/**
	 * Display a requested element using the model layer.
	 * <p>
	 * Can present a maze, a solution, and a cross section.
	 */
	private class Display implements Command
	{
		@Override
		public void doCommand() {
			if (arguments.size() == 2)
			{
				view.displayData(model.display(arguments.get(1)));				
			}
			else if ((arguments.get(1).toLowerCase().equals("cross")) && (arguments.get(2).toLowerCase().equals("section")) &&
					(arguments.get(3).toLowerCase().equals("by")) && ((arguments.get(4).toLowerCase().equals("x")) || 
					(arguments.get(4).toLowerCase().equals("y")) || (arguments.get(4).toLowerCase().equals("z"))) &&
					isNumeric(arguments.get(5)) && arguments.get(6).toLowerCase().equals("for") && (arguments.size() == 8))
			{
				view.displayData(model.displayCrossSection(arguments.get(4), Integer.parseInt(arguments.get(5)), arguments.get(7)));
			}
			else if ((arguments.get(1).toLowerCase().equals("solution")) && (arguments.size() == 3))
			{
				view.displayData(model.displaySolution(arguments.get(2)));
			}
		}
	}
	
	/**
	 * Runs the Save Maze operation in the model layer.
	 */
	private class SaveMaze implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("maze")) && (arguments.size() >= 4))
			{
				if (arguments.size() > 4)
				{
					String filePath = arguments.get(3);
					for (int i = 4; i < arguments.size(); i++)
					{
						filePath = filePath + " " + arguments.get(i);
					}
					model.saveMaze(arguments.get(2), filePath);
				}
				else
				{
					model.saveMaze(arguments.get(2), arguments.get(3));			
				}						
			}
			else
			{
				wrongInput();				
			}
		}
	}
	
	/**
	 * Runs the dir operation in the model layer.
	 */
	private class Dir implements Command
	{
		@Override
		public void doCommand() {
			if (arguments.size() == 2)
			{
				view.displayData(model.dir(arguments.get(1)));
			}
			else
			{
				wrongInput();
			}
		}
	}
	
	/**
	 * Runs the Maze Size operation in the model layer.
	 */
	private class MazeSize implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("size")) && (arguments.size() == 3))
				model.mazeSize(arguments.get(2));
			else
				wrongInput();
		}
	}
	
	/**
	 * Runs the Load Maze operation in the model layer.
	 */
	public class LoadMaze implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("maze")) && (arguments.size() >= 4))
			{
				if (arguments.size() > 4)
				{
					String filePath = arguments.get(2);
					for (int i = 3; i < arguments.size() - 1; i++)
					{
						filePath = filePath + " " + arguments.get(i);
					}
					model.loadMaze(filePath, arguments.get(arguments.size() - 1));
				}
				else
				{
					model.loadMaze(arguments.get(2), arguments.get(3));					
				}
			}
			else
			{
				wrongInput();
			}
		}
	}
	
	/**
	 * Runs the File Size operation in the model layer.
	 */
	public class FileSize implements Command{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("size")) && (arguments.size() == 3))
			{
				model.fileSize(arguments.get(2));
			}
			else if ((arguments.get(1).equals("size")) && (arguments.size() > 3))
			{
				String path = arguments.get(2);
				for (int i = 3; i < arguments.size(); i++)
				{
					path += " " + arguments.get(i);
				}
				model.fileSize(path);
			}
			else
			{
				wrongInput();
			}
		}
	}
	
	/**
	 * Runs the Solve operation in the model layer.
	 */
	public class Solve implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.size() == 3) && ((arguments.get(2).toLowerCase().equals("bfs")) || 
					(arguments.get(2).toLowerCase().equals("manhattan")) || (arguments.get(2).toLowerCase().equals("air"))))
			{
				model.solve(arguments.get(1), arguments.get(2));		
			}
			else
			{
				wrongInput();
			}
		}
	}
	
	/**
	 * Runs the Save Map operation in the model layer.
	 */
	public class SaveMap implements Command{

		@Override
		public void doCommand() {
			model.saveMap();
		}
	}
	
	/**
	 * Runs the Load Map operation in the model layer.
	 */
	public class LoadMap implements Command{

		@Override
		public void doCommand() {
			model.loadMap();
		}
	}
	
	/**
	 * Runs the Close Server operation in the model layer.
	 */
	public class CloseThreadPool implements Command{

		@Override
		public void doCommand() {
			model.closeThreadPool();
		}
	}	
}
