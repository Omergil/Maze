package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A basic window to be used by all windows created in the program.
 */
public abstract class BasicWindow extends Observable implements Runnable {

	Display display;
	Shell shell;
	
	/**
	 * Constructor to set window size.
	 * @param width
	 * @param height
	 */
	public BasicWindow(int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
	}
	
	/**
	 * Sets all widgets in the window.
	 */
	abstract void initWidgets();
	
	/**
	 * Runs the thread.
	 */
	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed
		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed
		 display.dispose(); // dispose OS components
	}
}
