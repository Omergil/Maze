package model;

public interface Model {
	int[][][] getData();
	void generate(String name, int width, int height, int floors);
	int[][][] display(String name);
	void savemaze(String name, String filename);//Daniel
	String[] dir(String path); //daniel
	void mazesize(String name); //daniel
}
