package boot;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import view.CLI;

public class Run {

	public static void main(String[] args) throws InterruptedException {
		BufferedReader input;
		PrintWriter output;
		Command mycommand = new Command();
		//CLI st = new CLI(st);
		Thread t1 = new Thread(st);
	
		t1.start();
		
		HashMap<String, Command> map = new HashMap<String, Command>();
		map
	}

}
