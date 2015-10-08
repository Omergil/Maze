package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GUIMazeSizeDialog extends GUISubMenu {

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUIMazeSizeDialog(Shell parent)
	{
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUIMazeSizeDialog(Shell parent, int style) {
	// Let users override the default styles
	super(parent, style);
	setText("Maze size");
	}
	
	/**
	 * Creates the contents of the sub menu.
	 * @param shell the dialog window
	 */
	@Override
	protected void createContents(Shell shell) {
		shell.setLayout(new GridLayout(2, true));
	    
	    // Display maze name label
	    Label mazeNameLabel = new Label(shell, SWT.NONE);
	    mazeNameLabel.setText("Maze name:");
	    mazeNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text mazeNameText = new Text(shell, SWT.BORDER);
	    mazeNameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));	 
	    mazeNameText.addListener(SWT.Verify, new Listener()
	    {
		    @Override
			public void handleEvent(Event e)
		    {
		    	e = checkSpaces(e);
			}
		});
    
	    // Create the OK button and add a handler, so that pressing it will set the HashMap with the user's input
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    ok.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event)
	      {
	    	  input = new HashMap<String,String>();
	    	  input.put("mazeName", mazeNameText.getText());
	    	  shell.close();
	      }
	    });

	    // Create the cancel button and add a handler so that pressing it will set the HashMap to null
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, true, 1, 1));
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	    });

	    // Set the OK button as default
	    shell.setDefaultButton(ok);	 
		}
}
