package model;

import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;

public class Maze3dModel extends Observable implements Model {

	int [][][] test = new int[1][2][3];// DELETE
	HashMap<String, Maze3d> mazeStore = new HashMap<String, Maze3d>();
	
	@Override
	public int[][][] getData() {
		return test;
	}

	@Override
	public void generate(String name, int width, int height, int floors) {
		mazeStore.put(name, new MyMaze3dGenerator(width, height, floors).generate());
		setChanged();
		notifyObservers("maze " + name + " is ready");
	}
}
