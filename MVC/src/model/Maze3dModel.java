package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.instrument.Instrumentation;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Executor;

import javax.naming.SizeLimitExceededException;

import algorithms.demo.Maze3dSearchable;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class Maze3dModel extends Observable implements Model {

	int [][][] test = new int[1][2][3];// DELETE
	HashMap<String, Maze3d> mazeStore = new HashMap<String, Maze3d>();
	HashMap<String, Solution> solutionsStore = new HashMap<String, Solution>();
	private static Instrumentation inst;
	
	@Override
	public int[][][] getData() {
		return test;
	}

	@Override
	public void generate(String name, int width, int height, int floors) {
		if ((width >= 2) && (height >= 2) && (floors >= 2))
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					mazeStore.put(name, new MyMaze3dGenerator(width, height, floors).generate());
					setChanged();
					notifyObservers("maze " + name + " is ready");	
				}
			}).start();		
		}
		else
		{
			setChanged();
			notifyObservers("Invalid maze size.");
		}
	}

	//OMER
	@Override
	public int[][][] display(String name) {
		return mazeStore.get(name).getMaze3d();
	}
	
	@Override
	public int[][] displayCrossSection(String coordinate, int index, String maze)
	{
		if (mazeStore.containsKey(maze))
		{
			if (coordinate.toLowerCase().equals("x"))
			{
				return mazeStore.get(maze).getCrossSectionByX(index);
			}
			else if (coordinate.toLowerCase().equals("y"))
			{
				return mazeStore.get(maze).getCrossSectionByY(index);
			}
			else if (coordinate.toLowerCase().equals("z"))
			{
				return mazeStore.get(maze).getCrossSectionByZ(index);
			}
		}
		setChanged();
		notifyObservers("Unable to complete operation.");
		return null;
	}

	//Daniel
	@Override
	public void saveMaze(String name, String filename) {
		//Check that the maze exist in the store
		if (!mazeStore.containsKey(name))
		{
			setChanged();
			notifyObservers("Maze does not exist.");
		}
		//Save the maze in file
		else
		{
			try{
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(filename));
				out.write(mazeStore.get(name).toByteArray());
				out.flush();
				out.close();
				setChanged();
				notifyObservers("Maze " + name + " saved.");
			}catch(Exception e){
				setChanged();
				notifyObservers("Maze could not be saved.");}
		}
	}

	//daniel
	@Override
	public String[] dir(String path) {
		String[] fileslist;
		try{
			File file = new File(path);
			file.toPath();
			//check that the path exist
			if (file.exists()){
				fileslist = file.list();
				return fileslist;
			}
			//return error if path does not exist
			else
			{
				setChanged();
				notifyObservers("Path does not exist.");
				return null;
			} 
		}catch(Exception e){
			setChanged();
			notifyObservers("Could not display files.");
			return null;
		}
	}

	//daniel
	@Override
	public void mazeSize(String name) {
		Maze3d temp;
		long size;
		//Check that the maze exist
		if (!mazeStore.containsKey(name))
		{
			setChanged();
			notifyObservers("The maze does not exist.");
		}
		//calculate the size of maze
		else
		{
			temp = mazeStore.get(name);
			size = temp.toByteArray().length;
			setChanged();
			notifyObservers("\"" + name + "\" maze size: " + size + " bytes.");
		}
	}

	@Override
	public void loadMaze(String filename, String name) {
		//check if the file does not exist
		if (!new File(filename).exists()){
			setChanged();
			notifyObservers("The file does not exist");
		}
		//load maze from file and save in store
		else
		{
			try{
				InputStream in = new MyDecompressorInputStream(new FileInputStream(filename));
				byte b[] = new byte[(int) new File(filename).length()];
				in.read(b);
				Maze3d load = new Maze3d(b);
				mazeStore.put(name, load);
				in.close();
				setChanged();
				notifyObservers("Maze " + name + " was saved.");
			}catch(Exception e){
				setChanged();
				notifyObservers("Could not load maze");}
		}
	}

	@Override
	public void fileSize(String filename) {
		//check if the file does not exist
		if (!new File(filename).exists()){
			setChanged();
			notifyObservers("The file does not exist");
		}
		//check the maze size in the file
		else
		{
			File file = new File(filename);
			long size = file.length();
			setChanged();
			notifyObservers("File " + filename + " size: " + size + " bytes.");
		}
	}
	
	@Override
	public void solve(String maze, String algorithm) {
		if (mazeStore.containsKey(maze))
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					if (algorithm.toLowerCase().equals("bfs"))
					{
						solutionsStore.put(maze, new BFS().search(new Maze3dSearchable(mazeStore.get(maze))));
					}
					else if (algorithm.toLowerCase().equals("manhattan"))
					{
						solutionsStore.put(maze, new AStar().search(new Maze3dSearchable(mazeStore.get(maze)), new MazeManhattanDistance()));
					}
					else if (algorithm.toLowerCase().equals("air"))
					{
						solutionsStore.put(maze, new AStar().search(new Maze3dSearchable(mazeStore.get(maze)), new MazeAirDistance()));
					}
					setChanged();
					notifyObservers("Solution for " + maze + " is ready.");
				}
			}).start();
		}
		else
		{
			setChanged();
			notifyObservers("Maze does not exist.");
		}
	}
	
	@Override
	public ArrayList<String> displaySolution(String maze) {
		if (solutionsStore.containsKey(maze))
		{
			ArrayList<State> solutionStates = solutionsStore.get(maze).getSolution();
			ArrayList<String> solutionStrings = new ArrayList<String>();
			for (State state : solutionStates)
			{
				solutionStrings.add(state.getState());
			}
			return solutionStrings;
		}
		setChanged();
		notifyObservers("Unable to complete operation.");
		return null;
	}

	@Override
	public void exit() {
		Runtime.getRuntime().exit(0);
		setChanged();
		notifyObservers("End of Session");
	}
}
