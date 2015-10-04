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

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class Maze3dModel extends Observable implements Model {

	int [][][] test = new int[1][2][3];// DELETE
	HashMap<String, Maze3d> mazeStore = new HashMap<String, Maze3d>();
	private static Instrumentation inst;
	
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
		//Check that the maze exist in the store
		if (mazeStore.get(name).equals(null))
		{
			setChanged();
			notifyObservers("the maze you selected is not exist in the maze store");
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
				notifyObservers("maze " + name + " was saved to " + filename);
			}catch(Exception e){
				setChanged();
				notifyObservers("maze could not be saved");}
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
				setChanged();
				notifyObservers("path " + path + " files are: ");
				return fileslist;
			}
			//return error if path does not exist
			else
			{
				setChanged();
				notifyObservers("path does not exist");
				return null;
			} 
		}catch(Exception e){
			setChanged();
			notifyObservers("could not display files");
			return null;
		}
	}

	//daniel
	@Override
	public void mazesize(String name) {
		Maze3d temp;
		long size;
		//Check that the maze exist
		if (mazeStore.get(name).equals(null))
		{
			setChanged();
			notifyObservers("the maze does not exist in the maze store");
		}
		//calculate the size of maze
		else
		{
			temp = mazeStore.get(name);
			size = temp.toByteArray().length;
			setChanged();
			notifyObservers("bytes used by " + name + " " + size);
		}
	}

	@Override
	public void loadmaze(String filename, String name) {
		//check if the file does not exist
		if (!new File(filename).exists()){
			setChanged();
			notifyObservers("the file does not exist");
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
				notifyObservers("maze " + name + " was saved");
			}catch(Exception e){
				setChanged();
				notifyObservers("could not load maze");}
		}
	}

	@Override
	public void filesize(String filename) {
		//check if the file does not exist
		if (!new File(filename).exists()){
			setChanged();
			notifyObservers("the file does not exist");
		}
		//check the maze size in the file
		else
		{
			File file = new File(filename);
			long size = file.length();
			setChanged();
			notifyObservers("The maze size in file " + filename + " is " + size);
		}
	}

	@Override
	public void exit() {
		Runtime.getRuntime().exit(0);
		setChanged();
		notifyObservers("End of Session");
	}

	
}
