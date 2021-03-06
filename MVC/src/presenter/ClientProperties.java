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
public class ClientProperties implements Serializable {
	
	String filePath;
	boolean propertiesSet = false;
	String mazeSolvingAlgorithm;
	String mazeName;
	int mazeWidth;
	int mazeHeight;
	int mazeFloors;
	String view;
	String host;
	int port;
	
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
	 * Gets the selected host property.
	 * @return String - host.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the selected host property.
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the selected port property.
	 * @return int - port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the selected port property.
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Default constructor.
	 */
	public ClientProperties(String filePath) {
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
			mazeSolvingAlgorithm = "bfs";
			mazeName = "mymaze";
			mazeWidth = 10;
			mazeHeight = 8;
			mazeFloors = 3;
			view = "GUI";
			host = "localhost";
			port = 9999;
			e.writeObject(mazeSolvingAlgorithm);
			e.writeObject(mazeName);
			e.writeObject(mazeWidth);
			e.writeObject(mazeHeight);
			e.writeObject(mazeFloors);
			e.writeObject(view);
			e.writeObject(host);
			e.writeObject(port);
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
				this.mazeSolvingAlgorithm = (String)d.readObject();
				this.mazeName = (String)d.readObject();
				this.mazeWidth = (int)d.readObject();
				this.mazeHeight = (int)d.readObject();
				this.mazeFloors = (int)d.readObject();
				this.view = (String)d.readObject();
				this.host = (String)d.readObject();
				this.port = (int)d.readObject();
				propertiesSet = true;
			} catch (Exception e) {
				save();
			}
		} catch (FileNotFoundException e) {
			save();
		}
	}
}
