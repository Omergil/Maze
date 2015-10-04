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
	
	//OMER //Daniel
	private void setHashMap() {
		hashMap.put("generate", new Generate());
		hashMap.put("help", new Help());
		hashMap.put("display", new Display());
		hashMap.put("save", new SaveMaze());
		hashMap.put("dir", new Dir());
		hashMap.put("maze", new MazeSize());
		hashMap.put("load", new LoadMaze());
		hashMap.put("file", new FileSize());
		hashMap.put("exit", new Exit());
	}

	//OMER
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
		
		else if (observable == model)
		{
			if (arg1 instanceof String)
			{
				ui.displayData(arg1);				
			}
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
		ui.displayData("Sorry, wrong command.");
	}
	
	//OMER
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
	
	//OMER //Daniel
	private class Help implements Command
	{
		@Override
		public void doCommand() {
			ui.displayData("Available commands:\n" +
					"generate 3d maze <name> <width> <height> <floors>\n" +
					"display <name>\n" +
					"save maze <name> <file name>\n" +
					"dir <path>\n" +
					"maze size <name>\n" +
					"load maze <filename> <name>\n" +
					"file size <name>\n" +
					"exit");
		}
	}
	
	//OMER
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
	
	//OMER
	private class Display implements Command
	{
		@Override
		public void doCommand() {
			ui.displayData(model.display(arguments.get(1)));
		}
	}
	
	//Daniel
	private class SaveMaze implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("maze")) && (arguments.size() == 4))	
				model.savemaze(arguments.get(2), arguments.get(3));
			else
				wrongInput();
		}
	}
	
	//Daniel
	private class Dir implements Command
	{
		@Override
		public void doCommand() {
			if (arguments.size() == 2)
				ui.displayFiles(model.dir(arguments.get(1)));
			else
				wrongInput();
		}
	}
	
	//Daniel
	private class MazeSize implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("size")) && (arguments.size() == 3))
				model.mazesize(arguments.get(2));
			else
				wrongInput();
		}
	}
	
	public class LoadMaze implements Command
	{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("maze")) && (arguments.size() == 4))
				model.loadmaze(arguments.get(2), arguments.get(3));
			else
				wrongInput();
		}
	}
	
	public class FileSize implements Command{
		@Override
		public void doCommand() {
			if ((arguments.get(1).equals("size")) && (arguments.size() == 3))
				model.filesize(arguments.get(2));
			else
				wrongInput();
		}
	}
	
	public class Exit implements Command{

		@Override
		public void doCommand() {
			if(arguments.size() == 1)
				model.exit();
			else
				wrongInput();
		}
	}
	
	
}
