package view;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

	void handleClient(InputStream inFromClient, OutputStream outToClient);
	void displayData(Object data);
	String getUserCommand();
	void setUserInput(String userInput);
}
