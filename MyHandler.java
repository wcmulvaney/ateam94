package application;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MyHandler implements EventHandler<ActionEvent> {
	Button button;
	
	/**
	 * Constructor that sets button field to button passed in
	 * @param button
	 */
	MyHandler(Button button){
		this.button = button;
	}
	@Override
	public void handle(ActionEvent arg0) {
		if(button.getText().equals("Home")) {
			
		}
		
	}

}
