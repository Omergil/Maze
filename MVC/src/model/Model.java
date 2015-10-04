package model;

import java.util.ArrayList;

public interface Model {
	int[][][] getData();
	String[] dir (String path); //daniel
	void generate(String name, int width, int height, int floors);
	int[][][] display(String name);
	int[][] displayCrossSection(String coordinate, int index, String maze);
	void saveMaze(String name, String filename);//Daniel
	void loadMaze (String filename, String name);//Daniel
	void mazeSize (String name); //daniel
	void fileSize (String filename); //daniel
	void solve (String maze, String algorithm);
	ArrayList<String> displaySolution (String maze);
	void exit(); //daniel

}
