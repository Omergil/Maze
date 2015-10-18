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
	public void sendToServer(String userInput);
	public void getFromServer();
	public void loadProperties(String filePath);
}
