package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import presenter.Command;

public class CLI extends Thread{
	BufferedReader in;
	PrintWriter out;
	View view;
	String line;
	
	public void setView(View view) {
		this.view = view;
	}

	public String getLine() {
		return line;
	}

	//Default Constructor
	public CLI(InputStream in, OutputStream out){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(new OutputStreamWriter(out));
	}

	// Get input from user until 'exit'
	private void runCLIInThread()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					while(!(line=in.readLine()).equals("exit")){
						view.setUserInput(line);
					}
				}catch(Exception e){}
				System.out.println("Good bye!");
			}
		}).start();
	}
	
	public void run() {
		runCLIInThread();
	}
}
