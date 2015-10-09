package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to set all data needed for the Save Maze operation.
 */
public class GUISaveMazeDialog extends GUISubMenu {

	private static final String[] FILTER_TYPES = {"Maze3d files (*.maz)"};
	private static final String[] FILTER_SUFFIX = {"*.maz"};
	
	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUISaveMazeDialog(Shell parent)
	{
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUISaveMazeDialog(Shell parent, int style)
	{
		// Let users override the default styles
		super(parent, style);
		setText("Save maze");
	}
 	
	/**
	 * Creates the contents of the sub menu.
	 * @param shell the dialog window
	 */
	@Override
	protected void createContents(Shell shell)
	{
		shell.setLayout(new GridLayout(3, true));
	    
	    // Display maze name label
	    Label mazeNameLabel = new Label(shell, SWT.NONE);
	    mazeNameLabel.setText("Maze name:");
	    mazeNameLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 3, 1));
	    
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
	    
	 // Display file path label
	    Label filePathLabel = new Label(shell, SWT.NONE);
	    filePathLabel.setText("File path:");
	    filePathLabel.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 3, 1));
	    
	    // Display the file path box
	    final Text filePathText = new Text(shell, SWT.BORDER);
	    filePathText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 2, 1));	 
    
	    // Display the browse button to select where to save the maze
	    Button browse = new Button(shell, SWT.PUSH);
	    browse.setText("Browse...");
	    browse.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    browse.addSelectionListener(new SelectionAdapter()
	    {
	    	public void widgetSelected(SelectionEvent event)
	    	{
	    		FileDialog dlg = new FileDialog(shell, SWT.SAVE);
	    		dlg.setFilterNames(FILTER_TYPES);
	    		dlg.setFilterExtensions(FILTER_SUFFIX);
	    		String fn = dlg.open();
	    		if (fn != null)
	    		{
	    			filePathText.setText(fn);
	    		}
	    	}
	    });
	    
	    // Create the OK button and add a handler,
	    // so that pressing it will set the HashMap with the user's input
	    Button save = new Button(shell, SWT.PUSH);
	    save.setText("Save");
	    save.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true, 1, 1));
	    save.addSelectionListener(new SelectionAdapter()
	    {
	    	public void widgetSelected(SelectionEvent event)
	    	{
	    		input = new HashMap<String,String>();
	    		input.put("mazeName", mazeNameText.getText());
	    		input.put("filePath", filePathText.getText());
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

	    // Set the Save button as default
	    shell.setDefaultButton(save);
	}
}
