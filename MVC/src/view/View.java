package view;

public interface View {
	void displayData(int[][][] data);
	void displayMessage(String message);
	String getUserCommand();
	void setUserInput(String userInput);
}
