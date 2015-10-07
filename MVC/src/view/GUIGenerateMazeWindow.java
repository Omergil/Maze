package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GUIGenerateMazeWindow extends Dialog {

	private HashMap<String, String> input = new HashMap<String,String>();

	/**
	* InputDialog constructor
	* 
	* @param parent the parent
	*/
	public GUIGenerateMazeWindow(Shell parent)
	{
	// Pass the default styles here
	this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	* InputDialog constructor
	* @param parent the parent
	   * @param style the style
	   */
	public GUIGenerateMazeWindow(Shell parent, int style) {
	// Let users override the default styles
	super(parent, style);
	setText("Generate maze");
	}

	  /**
	   * Opens the dialog and returns the input
	   * 
	   * @return String
	   */
	public HashMap<String, String> open() {
	// Create the dialog window
	Shell shell = new Shell(getParent(), getStyle());
	shell.setText(getText());
	createContents(shell);
	shell.pack();
	shell.open();
	Display display = getParent().getDisplay();
	while (!shell.isDisposed()) {
	  if (!display.readAndDispatch())
	  {
	    display.sleep();
      }
    }
    // Return the entered value, or null
    return input;
	}

	  /**
	   * Creates the dialog's contents
	   * 
	   * @param shell the dialog window
	   */
	  private void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(2, true));

	    // Display maze name label
	    Label messageLabel = new Label(shell, SWT.NONE);
	    messageLabel.setText("Please set the maze properties:");
	    messageLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display maze name label
	    Label mazeNameLabel = new Label(shell, SWT.NONE);
	    mazeNameLabel.setText("Name:");
	    mazeNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text mazeNameText = new Text(shell, SWT.BORDER);
	    mazeNameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));	    
	    
	    // Display maze name label
	    Label xLabel = new Label(shell, SWT.NONE);
	    xLabel.setText("X:");
	    xLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text xText = new Text(shell, SWT.BORDER);
	    xText.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    xText.addListener(SWT.Verify, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				e = checkIfNumeric(e);
			}
		});
	        
	    // Display maze name label
	    Label yLabel = new Label(shell, SWT.NONE);
	    yLabel.setText("Y:");
	    yLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text yText = new Text(shell, SWT.BORDER);
	    yText.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    yText.addListener(SWT.Verify, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				e = checkIfNumeric(e);
			}
		});
        
	    // Display maze name label
	    Label floorsLabel = new Label(shell, SWT.NONE);
	    floorsLabel.setText("Floors:");
	    floorsLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text floorsText = new Text(shell, SWT.BORDER);
	    floorsText.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    floorsText.addListener(SWT.Verify, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				e = checkIfNumeric(e);
			}
		});
    
	    // Create the OK button and add a handler
	    // so that pressing it will set input
	    // to the entered value
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    ok.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input.put("mazeName", mazeNameText.getText());
	        input.put("x", xText.getText());
	        input.put("y", xText.getText());
	        input.put("floors", xText.getText());
	        shell.close();
	      }
	    });

	    // Create the cancel button and add a handler
	    // so that pressing it will set input to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, true, 1, 1));
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    // Set the OK button as the default, so
	    // user can type input and press Enter
	    // to dismiss
	    shell.setDefaultButton(ok);
	  }
	  
	  private Event checkIfNumeric(Event e)
	  {
		  String string = e.text;
		  char[] chars = new char[string.length()];
		  string.getChars(0, chars.length, chars, 0);
		  for (int i = 0; i < chars.length; i++)
		  {
			  if (!('0' <= chars[i] && chars[i] <= '9'))
			  {
				  e.doit = false;
				  return e;
			  }
		  }
		  return e;
	  }
}
