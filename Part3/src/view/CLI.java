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

import controller.Command;

public class CLI implements Runnable{
	BufferedReader in;
	PrintWriter out;
	HashMap<String,Command> myMap;
	
	//Default Constructor
	public CLI(InputStream in, OutputStream out,HashMap<String,Command> hashmap){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(new OutputStreamWriter(out));
		this.myMap = hashmap;
	}

	@Override
	//Get input from user until 'exit'
	public void run() {
		String input = null;
		//Read string from in and put it in input
		try {
			input = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//check if input equals exit
		while (input != "exit"){
			myMap.get(input);
		}	
	}
	
}
