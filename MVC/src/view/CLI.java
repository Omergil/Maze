package view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Generic CLI.
 * <p>
 * Generic Command Line Interface to be used by a client to insert input to the program,
 * and use the input by the View layer.<br>
 * The CLI can use different types of InputStreamReader and OutputStreamWriter.
 */
public class CLI extends Thread{
	BufferedReader in;
	PrintWriter out;
	View view;
	String line;
	
	/**
	 * Setter for the View interface.
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Getter for the user input (line).
	 * @return
	 */
	public String getLine() {
		return line;
	}

	/**
	 * Constructor for the CLI.
	 * @param in
	 * @param out
	 */
	public CLI(InputStream in, OutputStream out){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(new OutputStreamWriter(out));
	}

	/**
	 * I/O operation of the CLI.
	 * <p>
	 * Receives input from the user until the string 'exit' is received.<br>
	 * The operation runs in a new thread.
	 */
	private void runCLIInThread()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					System.out.println("Type \"help\" for help.");
					while(!(line=in.readLine()).equals("exit!")){
						view.setUserInput(line);
					}
				}catch(Exception e){}
				System.out.println("Good bye!");
			}
		}).start();
	}
	
	/**
	 * Runs the thread for the CLI.
	 */
	public void run() {
		runCLIInThread();
	}
}
