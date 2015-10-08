package view;

import java.util.HashMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public abstract class GUISubMenu extends Dialog {

	protected HashMap<String, String> input = null;
	
	/**
	 * Constructor to use with predefined style.
	 * @param parent
	 */
	public GUISubMenu(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Constructor to use with manual style definition.
	 * @param parent
	 * @param style
	 */
	public GUISubMenu(Shell parent, int style) {
		super(parent, style);
	}

	/**
	* Opens the dialog.
	* 
	* @return User input. HashMap<String, String>
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
	 * Creates the contents of the sub menu.
	 * @param shell
	 */
	protected abstract void createContents(final Shell shell);
	
	/**
	 * Checks if an input in a text field is numeric.
	 * <p>
	 * Sets e.doit to false in case an input is not numeric.
	 * @param e
	 * @return Event.
	 */
	protected Event checkIfNumeric(Event e)
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
	
	/**
	 * Checks if an input in a text field contains white spaces.
	 * <p>
	 * Sets e.doit to false in case an input contains white spaces.
	 * @param e
	 * @return Event.
	 */
	protected Event checkSpaces(Event e)
	{
		String string = e.text;
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++)
		{
			if (Character.isSpaceChar(chars[i]))
			{
				e.doit = false;
				return e;
			}
		}
		return e;
	}
}
