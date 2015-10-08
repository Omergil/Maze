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

public class GUISolveMazeDialog extends GUISubMenu {

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUISolveMazeDialog(Shell parent)
	{
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUISolveMazeDialog(Shell parent, int style)
	{
		// Let users override the default styles
		super(parent, style);
		setText("Solve maze");
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
	    mazeNameLabel.setText("Name:");
	    mazeNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // Display the maze name box
	    final Text mazeNameText = new Text(shell, SWT.BORDER);
	    mazeNameText.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));	 
	    mazeNameText.addListener(SWT.Verify, new Listener()
	    {
		    @Override
			public void handleEvent(Event e)
		    {
		    	e = checkSpaces(e);
			}
		});
	    
	    // Display the solution type label
	    Label solutionTypeLabel = new Label(shell, SWT.NONE);
	    solutionTypeLabel.setText("Name:");
	    solutionTypeLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 1));
	    
	    // BFS algorithm
	    Button bfs = new Button(shell, SWT.RADIO);
	    bfs.setText("BFS Algorithm");
	    bfs.setLayoutData(new GridData(SWT.NONE, SWT.None, true, true, 2, 1));
	    
	    // A* algorithm with Manhattan heuristic
	    Button manhattan = new Button(shell, SWT.RADIO);
	    manhattan.setText("A* Algorithm + Manhattan heuristic method");
	    manhattan.setLayoutData(new GridData(SWT.NONE, SWT.None, true, true, 2, 1));
	    
	    // A* algorithm with Air Distance heuristic
	    Button air = new Button(shell, SWT.RADIO);
	    air.setText("A* Algorithm + Air Distance heuristic method");
	    air.setLayoutData(new GridData(SWT.NONE, SWT.None, true, true, 2, 1));
    
	    // Create the OK button and add a handler, so that pressing it will set the HashMap with the user's input
	    Button solve = new Button(shell, SWT.PUSH);
	    solve.setText("Solve");
	    solve.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    solve.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event)
	      {
	    	  input = new HashMap<String,String>();
	    	  input.put("mazeName", mazeNameText.getText());
	    	  if (bfs.getSelection())
	    		  input.put("algorithm", "bfs");
	    	  if (manhattan.getSelection())
	    		  input.put("algorithm", "manhattan");
	    	  if (air.getSelection())
	    		  input.put("algorithm", "air");
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
	    shell.setDefaultButton(solve);	  
	}
}
