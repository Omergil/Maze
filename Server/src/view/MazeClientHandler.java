package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;

public class MazeClientHandler extends Observable implements ClientHandler {

	String userInput;
	BufferedReader in;
	ObjectOutputStream out;
	
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try {
			in = new BufferedReader(new InputStreamReader(inFromClient));
			out = new ObjectOutputStream(outToClient);
			String line;
			
			while(!(line = in.readLine()).endsWith("exit"))
			{
				setUserInput(line);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayData(Object data) {
		try {
			out.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUserCommand() {
		return userInput;
	}

	@Override
	public void setUserInput(String userInput) {
		this.userInput = userInput;
		setChanged();
		notifyObservers();
	}

}
