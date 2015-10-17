package presenter;

import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;

public class ClientPresenter implements Observer {

	Model model;
	View ui;

	/**
	 * Constructor for presenter layer in MVP.
	 * @param model
	 * @param ui
	 */
	public ClientPresenter(Model model, View ui) {
		this.model = model;
		this.ui = ui;
	}

	/**
	 * Method used when an observable notifies the observer (the presenter).
	 * <p>
	 * If the notifier is the view layer - perform an operation using the model layer.<br>
	 * If the notifier is the model layer - display the results for the client in the view layer.
	 */
	@Override
	public void update(Observable observable, Object arg1) {
		if (observable == ui)
		{
			model.sendToServer(ui.getUserCommand());
		}
		
		else if (observable == model)
		{
			ui.displayData(arg1);
		}
	}	
}
