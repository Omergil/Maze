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
public class ServerProperties implements Serializable {
	
	String filePath;
	boolean propertiesSet = false;
	int numOfThreads;
	
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
	 * Default constructor.
	 */
	public ServerProperties(String filePath) {
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
			e.writeObject(numOfThreads);
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
				propertiesSet = true;
			} catch (Exception e) {
				save();
			}
		} catch (FileNotFoundException e) {
			save();
		}
	}
}
