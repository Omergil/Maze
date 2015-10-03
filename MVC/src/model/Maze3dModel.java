package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

import javax.naming.SizeLimitExceededException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

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

	//OMER
	@Override
	public int[][][] display(String name) {
		return mazeStore.get(name).getMaze3d();
	}

	//Daniel
	@Override
	public void savemaze(String name, String filename) {
		try{
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(filename));
			out.write(mazeStore.get(name).toByteArray());
			out.flush();
			out.close();
		}catch(IOException e){
			e.printStackTrace();}
		setChanged();
		notifyObservers("maze " + name + " was saved to " + filename);
	}

	//daniel
	@Override
	public String[] dir(String path) {
		String[] fileslist;
		File file = new File(path);
		file.toPath();
		fileslist = file.list();
		setChanged();
		notifyObservers("path " + path + " files are: ");
		return fileslist;
	}

	//daniel
	@Override
	public void mazesize(String name) {
		Instrumentation inst = null;
		setChanged();
		notifyObservers("bytes used by " + name + " " + inst.getObjectSize(mazeStore.get(name)));
	}
	
	
	
}
