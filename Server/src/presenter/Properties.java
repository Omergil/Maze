package presenter;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * Class for storing the program's properties.
 * <p>
 * The purpose of the class is to save the program's properties in an XML file, and load it when it starts.
 */
public class Properties implements Serializable {
	
	String filePath;
	boolean propertiesSet = false;
	int numOfThreads;
	String mazeSolvingAlgorithm;
	String mazeName;
	int mazeWidth;
	int mazeHeight;
	int mazeFloors;
	String view;
	
	/**
	 * Gets the file path.
	 * @return String - file path.
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Check if the properties were successfully set.
	 * @return True if the properties were successfully set.
	 */
	public boolean isPropertiesSet() {
		return propertiesSet;
	}

	/**
	 * Gets the maze name property.
	 * @return String - maze name.
	 */
	public String getMazeName() {
		return mazeName;
	}

	/**
	 * Sets the maze name property.
	 * @param mazeName
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	/**
	 * Gets the maze width property.
	 * @return Integer - maze width.
	 */
	public int getMazeWidth() {
		return mazeWidth;
	}

	/**
	 * Sets the maze width property.
	 * @param mazeWidth
	 */
	public void setMazeWidth(int mazeWidth) {
		this.mazeWidth = mazeWidth;
	}

	/**
	 * Gets the maze height property.
	 * @return Integer - maze height.
	 */
	public int getMazeHeight() {
		return mazeHeight;
	}

	/**
	 * Sets the maze height property.
	 * @param mazeHeight
	 */
	public void setMazeHeight(int mazeHeight) {
		this.mazeHeight = mazeHeight;
	}

	/**
	 * Gets the maze floors property.
	 * @return Integer - maze floors.
	 */
	public int getMazeFloors() {
		return mazeFloors;
	}

	/**
	 * Sets the maze floors property.
	 * @param mazeFloors
	 */
	public void setMazeFloors(int mazeFloors) {
		this.mazeFloors = mazeFloors;
	}

	/**
	 * Gets the program's number of maximum threads property.
	 * @return Integer - program's number of maximum threads.
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	/**
	 * Sets the program's number of threads property.
	 * @param numOfThreads
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	/**
	 * Gets the program's solution algorithm property.
	 * @return String - program's maze solution.
	 */
	public String getMazeSolvingAlgorithm() {
		return mazeSolvingAlgorithm;
	}

	/**
	 * Sets the maze solving algorithm property.
	 * @param mazeSolvingAlgorithm
	 */
	public void setMazeSolvingAlgorithm(String mazeSolvingAlgorithm) {
		this.mazeSolvingAlgorithm = mazeSolvingAlgorithm;
	}
	
	/**
	 * Gets the selected view property.
	 * @return String - selected view.
	 */
	public String getView() {
		return view;
	}

	/**
	 * Sets the selected view property.
	 * @param view
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * Default constructor.
	 */
	public Properties(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Creates a new properties XML file.
	 * <p>
	 * The file contains all data members of the class.
	 */
	public void save()
	{		
		XMLEncoder e;
		try {
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)));
			numOfThreads = 20;
			mazeSolvingAlgorithm = "bfs";
			mazeName = "mymaze";
			mazeWidth = 10;
			mazeHeight = 8;
			mazeFloors = 3;
			view = "GUI";
			e.writeObject(numOfThreads);
			e.writeObject(mazeSolvingAlgorithm);
			e.writeObject(mazeName);
			e.writeObject(mazeWidth);
			e.writeObject(mazeHeight);
			e.writeObject(mazeFloors);
			e.writeObject(view);
			e.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Loads all properties from the properties XML file.
	 * <p>
	 * All properties are inserted into the object's data members.<br>
	 * In case the properties file cannot be found, or an error occurs during deserialization,
	 * the method runs the save() method to create a new properties file,
	 * which also sets the data members with the default values.
	 */
	public void load()
	{
		XMLDecoder d;
		try {
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(filePath)));
			try {
				this.numOfThreads = (int)d.readObject();
				this.mazeSolvingAlgorithm = (String)d.readObject();
				this.mazeName = (String)d.readObject();
				this.mazeWidth = (int)d.readObject();
				this.mazeHeight = (int)d.readObject();
				this.mazeFloors = (int)d.readObject();
				this.view = (String)d.readObject();
				propertiesSet = true;
			} catch (Exception e) {
				save();
			}
		} catch (FileNotFoundException e) {
			save();
		}
	}
}
