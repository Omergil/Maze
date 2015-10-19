package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to set all data needed for the Generate Maze operation.
 */
public class GUIGenerateMazeDialog extends GUISubMenu {

	String defaultMazeName;
	String defaultX;
	String defaultY;
	String defaultFloors;
	
	/**
	 * Constructor to use with predefined style.
	 * @param parent
	 */
	public GUIGenerateMazeDialog(Shell parent, String mazeName, String x, String y, String floors)
	{
		// Pass the default styles here
		this(parent, mazeName, x, y, floors, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUIGenerateMazeDialog(Shell parent, String mazeName, String x, String y, String floors, int style)
	{
		// Let users override the default styles
		super(parent, style);
		setText("Generate maze");
		defaultMazeName = mazeName;
		defaultX = x;
		defaultY = y;
		defaultFloors = floors;
	}

	/**
	 * Creates the contents of the sub menu.
	 * @param shell the dialog window
	 */
	protected void createContents(final Shell shell)
	{
		shell.setLayout(new GridLayout(2, true));

		// Display the message label
		Label messageLabel = new Label(shell, SWT.NONE);
	    messageLabel.setText("Please set the maze properties.");
	    messageLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display maze name label
	    Label mazeNameLabel = new Label(shell, SWT.NONE);
	    mazeNameLabel.setText("Name:");
	    mazeNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text mazeNameText = new Text(shell, SWT.BORDER);
	    mazeNameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));
	    mazeNameText.setText(defaultMazeName);
	    mazeNameText.addListener(SWT.Verify, new Listener()
	    {
		    @Override
			public void handleEvent(Event e)
		    {
		    	e = checkSpaces(e);
			}
		});
	    
	    // Display the X label
	    Label xLabel = new Label(shell, SWT.NONE);
	    xLabel.setText("X:");
	    xLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the X box
	    final Text xText = new Text(shell, SWT.BORDER);
	    xText.setText(defaultX);
	    xText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));
	    xText.addListener(SWT.Verify, new Listener()
	    {
			@Override
			public void handleEvent(Event e)
			{
				e = checkIfNumeric(e);
			}
		});
	        
	    // Display the Y label
	    Label yLabel = new Label(shell, SWT.NONE);
	    yLabel.setText("Y:");
	    yLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the Y box
	    final Text yText = new Text(shell, SWT.BORDER);
	    yText.setText(defaultY);
	    yText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));
	    yText.addListener(SWT.Verify, new Listener()
	    {
	    	@Override
			public void handleEvent(Event e)
	    	{
				e = checkIfNumeric(e);
			}
		});
        
	    // Display the floors label
	    Label floorsLabel = new Label(shell, SWT.NONE);
	    floorsLabel.setText("Floors:");
	    floorsLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the floors box
	    final Text floorsText = new Text(shell, SWT.BORDER);
	    floorsText.setText(defaultFloors);
	    floorsText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));
	    floorsText.addListener(SWT.Verify, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				e = checkIfNumeric(e);
			}
		});
    
	    // Create the OK button and add a handler,
	    // so that pressing it will set the HashMap with the user's input
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    ok.addSelectionListener(new SelectionAdapter()
	    {
	      public void widgetSelected(SelectionEvent event)
	      {
	    	  input = new HashMap<String,String>();
	    	  input.put("mazeName", mazeNameText.getText());
	    	  input.put("x", xText.getText());
	    	  input.put("y", yText.getText());
	    	  input.put("floors", floorsText.getText());
	    	  shell.close();
	      }
	    });

	    // Create the cancel button and add a handler,
	    // so that pressing it will set the HashMap to null
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
