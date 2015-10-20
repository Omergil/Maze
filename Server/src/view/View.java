package view;

/**
 * Interface for the view layer in the MVP architecture.
 */
public interface View {
	void displayData(Object data);
	String getUserCommand();
	void setUserInput(String userInput);
}
