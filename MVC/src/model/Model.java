package model;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;

/**
 * Interface for Model layer in MVP architecture.
 * <p>
 * Contains all methods to be implemented.
 *
 */
public interface Model {
	String[] dir (String path);
	void generate(String name, int width, int height, int floors);
	Maze3d display(String name);
	int[][] displayCrossSection(String coordinate, int index, String maze);
	void saveMaze(String name, String filename);
	void loadMaze (String filename, String name);
	void mazeSize (String name);
	void fileSize (String filename);
	void solve (String maze, String algorithm);
	ArrayList<String> displaySolution (String maze);
	void exit();
	void saveMap();
	void loadMap();
	
}
