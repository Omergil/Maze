package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * A generic message box.
 * <p>
 * Receives a message and displays it in a message box.
 */
public class GUIMessageBox {

	/**
	 * Constructor to create the message box.
	 * @param parent
	 * @param message
	 */
	public GUIMessageBox(Shell parent, String message) {
		MessageBox messageBox = new MessageBox(parent, SWT.ICON_INFORMATION);
        messageBox.setMessage(message);
        messageBox.open();
	}
}
