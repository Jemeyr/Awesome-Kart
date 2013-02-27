package Controller;

import States.StateContext;
import net.java.games.input.Event;

public interface GameController {
	
	public void handleEvent(Event event, StateContext stateContext);
	
	void actionButton();
	void backButton();
	void weaponButton();
	void pauseButton();
	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();
	void moveAnalog();
	
}
