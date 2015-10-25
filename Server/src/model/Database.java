package model;

import java.util.HashMap;
import java.util.Map;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

/**
 * The database of the program.
 * <p>
 * Stores all data of the program.<br>
 * Data is stored and retrieved using Maze3d Model class.
 */
public class Database {

	HashMap<String, Maze3d> mazeStore = new HashMap<String, Maze3d>();
	HashMap<String, Solution> solutionsStore = new HashMap<String, Solution>();
	
	/**
	 * Gets the Maze Store hashmap.
	 * @return mazeStore hashmap
	 */
	public HashMap<String, Maze3d> getMazeStore() {
		return mazeStore;
	}
	
	/**
	 * Puts a key and value to the maze store.
	 * @param mazeName
	 * @param maze
	 */
	public void putInMazeStore(String mazeName, Maze3d maze) {
		mazeStore.put(mazeName, maze);
	}
	
	
	/**
	 * Puts a hashmap in the maze store.
	 * @param mazeName
	 * @param maze
	 */
	public void putAllInMazeStore(Map<String, Maze3d> map)
	{
		mazeStore.putAll(map);
	}
	
	/**
	 * Gets the Solution Store hashmap.
	 * @return solutionStore hashmap
	 */
	public HashMap<String, Solution> getSolutionsStore() {
		return solutionsStore;
	}
	
	/**
	 * Puts a key and value to the solution store.
	 * @param mazeName
	 * @param maze
	 */
	public void putInSolutionsStore(String mazeName, Solution solution) {
		solutionsStore.put(mazeName, solution);
	}
	
	
	/**
	 * Puts a hashmap in the solution store.
	 * @param mazeName
	 * @param maze
	 */
	public void putAllInSolutionStore(Map<String, Solution> map)
	{
		solutionsStore.putAll(map);
	}
	
}
