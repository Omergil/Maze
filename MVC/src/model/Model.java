package model;

public interface Model {
	int[][][] getData();
	void generate(String name, int width, int height, int floors);
	int[][][] display(String name);
}
