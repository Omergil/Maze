package model;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.omg.PortableInterceptor.INACTIVE;

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

/**
 * Model object for MVP architecture, specifically for Maze3d.
 */
public class Maze3dModel extends Observable implements Model {

	private static final int NUMOFTHREADS = 20;
	ArrayList<Object> solutionArray = new ArrayList<Object>();
	Object[] innerArray = new Object[3];
	ExecutorService exec;
	Database database;
	
	/**
	 * Default Constructor.
	 * <p>
	 * Sets the model with the default number of threads.
	 */
	public Maze3dModel(Database database) {
		exec = Executors.newFixedThreadPool(NUMOFTHREADS);
		this.database = database;		
	}

	/**
	 * Returns all files and folders for a given path on the file system.
	 * @return Array of Strings.
	 */
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
				notifyObservers("Path doesn't exist.");
				return null;
			} 
		}catch(Exception e){
			setChanged();
			notifyObservers("Could not display files.");
			return null;
		}
	}
	
	/**
	 * Generates a new maze.
	 * <p>
	 * The method generates a new Maze3d object.
	 * The Maze3d object is added to mazeStore HashMap, containing the maze name and Maze3d object.
	 * Notifies presenter when maze is ready.  
	 */
	@Override
	public void generate(String name, int width, int height, int floors) {
		Future<String> f = exec.submit(new Callable<String>(){
			@Override
			public String call() throws Exception {
				//check if the maze is already exist
				if (database.getMazeStore().containsKey(name))
				{
					return "Maze already exist.";
				}
				//check that maze size is valid
				else if ((width >= 2) && (height >= 2) && (floors >= 2))
				{
					database.putInMazeStore(name, new MyMaze3dGenerator(width, height, floors).generate());
					return "Maze " + name + " is ready.";
				}
				else
				{
					return "Invalid maze size.";
				}
			}});
		
		exec.execute(new Runnable() {
			
			@Override
			public void run() {
				try{
					String string = f.get();
					if (!string.isEmpty()){
						setChanged();
						notifyObservers(string);
					}
					else
					{
						setChanged();
						notifyObservers("Could not create maze.");
					}
						
				}catch(Exception e){
					setChanged();
					notifyObservers("Could not generate maze.");
				}
			}
		});		
			
	}

	/**
	 * Displays the maze to the view layer.
	 * @return Maze3d.
	 */
	@Override
	public Maze3d display(String name) {
		if (database.getMazeStore().containsKey(name))
		{
			return database.getMazeStore().get(name);
		}
		setChanged();
		notifyObservers("Maze doesn't exist.");
		return null;
	}
	
	/**
	 * Displays a cross section to the view layer.
	 * <p>
	 * Notifies the presenter if operation is failed. 
	 * @return 2d array containing cross section maze.
	 */
	@Override
	public int[][] displayCrossSection(String coordinate, int index, String maze)
	{
		if (database.getMazeStore().containsKey(maze))
		{
			if (coordinate.toLowerCase().equals("x"))
			{
				return database.getMazeStore().get(maze).getCrossSectionByX(index);
			}
			else if (coordinate.toLowerCase().equals("y"))
			{
				return database.getMazeStore().get(maze).getCrossSectionByY(index);
			}
			else if (coordinate.toLowerCase().equals("z"))
			{
				return database.getMazeStore().get(maze).getCrossSectionByZ(index);
			}
		}
		setChanged();
		notifyObservers("Unable to complete operation.");
		return null;
	}

	/**
	 * Saves a maze to a file.
	 * <p>
	 * The operation saves as existing maze from mazeStore to the file system, in a given path or filename.
	 * <br>
	 * Notifies the presenter if a maze doesn't exist, or when the operation is complete.
	 */
	@Override
	public void saveMaze(String name, String filename) {
		//Check that the maze exist in the store
		if (!database.getMazeStore().containsKey(name))
		{
			setChanged();
			notifyObservers("Maze doesn't exist.");
		}
		//Save the maze in file
		else
		{
			try{
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(filename));
				out.write(database.getMazeStore().get(name).toByteArray());
				out.flush();
				out.close();
				setChanged();
				notifyObservers("Maze " + name + " saved.");
			}catch(Exception e){
				setChanged();
				notifyObservers("Maze could not be saved.");}
		}
	}
	
	/**
	 * Loads a maze from a file to the memory.
	 * <p>
	 * A maze saved on the file system in loaded and saved in mazeStore.
	 */
	@Override
	public void loadMaze(String filename, String name) {
		//check if the file does not exist
		if (!new File(filename).exists()){
			setChanged();
			notifyObservers("The file doesn't exist");
		}
		//load maze from file and save in store
		else
		{
			try{
				InputStream in = new MyDecompressorInputStream(new FileInputStream(filename));
				byte b[] = new byte[(int) new File(filename).length()];
				in.read(b);
				Maze3d load = new Maze3d(b);
				database.putInMazeStore(name, load);
				in.close();
				setChanged();
				notifyObservers("Maze " + name + " was saved.");
			}catch(Exception e){
				setChanged();
				notifyObservers("Could not load maze");}
		}
	}

	/**
	 * Calculates a maze size.
	 * <p>
	 * Calculation of a Maze3d object in the memory.
	 * <br>
	 * Notifies the presenter with the size in bytes.
	 */
	@Override
	public void mazeSize(String name) {
		//Check that the maze exist
		if (!database.getMazeStore().containsKey(name))
		{
			setChanged();
			notifyObservers("The maze doesn't exist.");
		}
		//calculate the size of maze
		else
		{
			int size = database.getMazeStore().get(name).toByteArray().length * 4;
			setChanged();
			notifyObservers("\"" + name + "\" maze size: " + size + " bytes.");
		}
	}

	/**
	 * Return the size of a file in the file system.
	 * <br>
	 * Notifies the presenter with the size of the file.
	 */
	@Override
	public void fileSize(String filename) {
		//check if the file does not exist
		if (!new File(filename).exists()){
			setChanged();
			notifyObservers("The file doesn't exist");
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
	
	/**
	 * Solving a Maze3d challenge.
	 * <p>
	 * The operation receives a name of a Maze3d saved in mazeStore along with an algorithm to solve it.<br>
	 * Possible input for algorithm:<br>
	 * - <b>BFS</b> for BFS algorithm.<br>
	 * - <b>Manhattan</b> for A* algorithm using Manhattan heuristic method.<br>
	 * - <b>Air</b> for A* algorithm using Air Distance heuristic method.
	 * <p>
	 * The solution is saved in solutionStore - a map for a Maze3d name and a Solution for it.
	 */
	@Override
	public void solve(String maze, String algorithm) {
		Future<String> f = exec.submit(new Callable<String>(){

			@Override
			public String call() throws Exception {
				//Check if the solution already exist
				if (database.getSolutionsStore().containsKey(maze))
				{
					return "Solution already exists.";
				}
				//check that maze is in the store
				if (database.getMazeStore().containsKey(maze))
				{
					//solve maze by chosen solution
					if (algorithm.toLowerCase().equals("bfs"))
					{
						database.putInSolutionsStore(maze, new BFS().search(new Maze3dSearchable(database.getMazeStore().get(maze))));
						return "Solution for " + maze + " is ready.";
					}
					else if (algorithm.toLowerCase().equals("manhattan"))
					{
						database.putInSolutionsStore(maze, new AStar().search(new Maze3dSearchable(database.getMazeStore().get(maze)), new MazeManhattanDistance()));
						return "Solution for " + maze + " is ready.";
					}
					else if (algorithm.toLowerCase().equals("air"))
					{
						database.putInSolutionsStore(maze, new AStar().search(new Maze3dSearchable(database.getMazeStore().get(maze)), new MazeAirDistance()));
						return "Solution for " + maze + " is ready.";
					}
				}
				else
				{
					return "Maze doesn't exist.";
				}
				return "Could not solve maze.";
			}
		});
			exec.execute(new Runnable() {
				
				@Override
				public void run() {
					try{
						String string = f.get();
						if(!string.isEmpty())
						{
							setChanged();
							notifyObservers(string);
						}
						else
						{
							setChanged();
							notifyObservers("Could not solve maze.");	
						}
					}catch(Exception e){
						setChanged();
						notifyObservers("Could not solve maze.");
					}
			}});
	}
	
	
	/**
	 * Retrieves a solution from solutionStore.
	 * <p>
	 * @return ArrayList<String> containing coordinates for each step in the solution. 
	 */
	@Override
	public ArrayList<String> displaySolution(String maze) {
		if (database.getSolutionsStore().containsKey(maze))
		{
			ArrayList<State> solutionStates = database.getSolutionsStore().get(maze).getSolution();
			ArrayList<String> solutionStrings = new ArrayList<String>();
			for (int i = solutionStates.size() - 1; i >= 0; i--)
			{
				solutionStrings.add(solutionStates.get(i).getState());
			}
			return solutionStrings;
		}
		setChanged();
		notifyObservers("Unable to complete operation.");
		return null;
	}
	
	/**
	 * Compress object to bytes
	 * @return compressed object
	 */
	public static byte[] toCompressedBytes(Object o) throws IOException
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(new DeflaterOutputStream(b));
		os.writeObject(o);
		os.close();
		return b.toByteArray();
	}
	
	/**
	 * Decompress object
	 * @return uncompressed object
	 */
	public static Object deCompressedBytes(byte[] bytes) throws IOException, ClassNotFoundException
	{
		ObjectInputStream os = new ObjectInputStream(new InflaterInputStream(new ByteArrayInputStream(bytes)));
		return os.readObject();
	}
	
	/** 
	 * Save the map in zip file
	 * @throws IOException 
	 * 
	 */
	@Override
	public void saveMap()
	{
		boolean val1, val2;
		val1 = database.getSolutionsStore().isEmpty();
		val2 = database.getMazeStore().isEmpty();
		if(!(val1) && !(val2))
		{
				//turn stores to one array
				//create array of solution
				HashMap<String, Solution> temp = new HashMap<String,Solution>();
				HashMap<String,Maze3d> temp2 = new HashMap<String,Maze3d>();
				temp.putAll(database.getSolutionsStore());
				temp2.putAll(database.getMazeStore());
				
				//get solutions maze from solutionStore and put it to array
				Collection<Solution> solutionmaze;
				solutionmaze = temp.values();
				Object[] tempsolarray = solutionmaze.toArray();
				//get solutions maze name from solutionStore and put it to array
				Collection<String> solutionmazenames;
				solutionmazenames = temp.keySet();
				Object[] tempsolnamearray = solutionmazenames.toArray();
	
				//get maze3d object from mazeStore and put it to array
				Collection<Maze3d> maze3dobject;
				maze3dobject = temp2.values();
				Object[] tempmaze3darray = maze3dobject.toArray();
				//get maze3d name from mazeStore and put it to array
				Collection<String> maze3dnames;
				maze3dnames = temp2.keySet();
				Object[] tempmaze3namedarray = maze3dnames.toArray();
				
				for (int i=0; i < tempsolnamearray.length; i++)
				{
					innerArray[0] = tempsolnamearray[i];
					innerArray[1] = tempsolarray[i];
					solutionArray.add(innerArray);
				}
				
				for(int i=0; i < solutionArray.size(); i++)
				{
					for(int j=0; j<tempmaze3namedarray.length; j++)
					{
						if (innerArray[0].equals(tempmaze3namedarray[j]))
						{
							innerArray[2] = tempmaze3darray[j];
						}
							
					}
				}
			
				//save the solution array
				ObjectOutputStream os = null;
				try {
					//empty the file if it exist
					File f = new File("zipfile.zip");
					os = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("zipfile.zip")));
					if(f.exists())
						os.reset();
					os.writeObject(solutionArray);
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

	}
	
	/** 
	 * Open the map from zip file
	 * 
	 */
	@Override
	public void loadMap()
	{ 
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("zipfile.zip"));
			if((br.readLine() != null)){
				ObjectInputStream is = null;
				try {
						is = new ObjectInputStream(new GZIPInputStream(new FileInputStream("zipfile.zip")));
						solutionArray = (ArrayList<Object>)is.readObject();	
						
						//solArray = (Object[][])is.readObject();
						is.close();
						
						//turn the Object[][] to hash map for solution store
						HashMap<String, Solution> soltempmap = new HashMap<String, Solution>();
						HashMap<String, Maze3d> mazetempmap = new HashMap<String, Maze3d>();
						
						for(int i=0; i<solutionArray.size();i++)
						{
							innerArray = (Object[]) solutionArray.get(i);
							soltempmap.put(innerArray[0].toString(), (Solution)innerArray[1]);
						}
						database.putAllInSolutionStore(soltempmap);
						
						//turn the Object[][] to hash map for maze store
						for(int i=0; i<solutionArray.size();i++)
						{
							innerArray = (Object[]) solutionArray.get(i);
							mazetempmap.put(innerArray[0].toString(), (Maze3d)innerArray[2]);
						}	
						database.putAllInMazeStore(mazetempmap);
						
				} catch (IOException e) {
					e.printStackTrace();
					setChanged();
					notifyObservers("Cannot load map.");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Cannot load map.");
		}
	}
	
	/**
	 * Closes all threads in the model layer.
	 */
	@Override
	public void closeThreadPool() {
		exec.shutdown();
		try {
			boolean allTasksCompleted=false;
			while(!(allTasksCompleted=exec.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
}
