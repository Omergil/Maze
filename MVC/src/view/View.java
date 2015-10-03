package view;

public interface View {
	void displayData(Object data);
	String getUserCommand();
	void setUserInput(String userInput);
	void displayFiles(String[] fileslist);//Daniel
}
