package Controller;

import net.java.games.input.Event;

public class XboxController implements GameController {
	
	@Override
	public void actionButton() {
		System.out.println("You're pressing the A button!");
	}

	@Override
	public void backButton() {
	}

	@Override
	public void weaponButton() {
	}

	@Override
	public void pauseButton() {
	}

	@Override
	public void moveUp() {
	}

	@Override
	public void moveDown() {
	}

	@Override
	public void moveLeft() {
	}

	@Override
	public void moveRight() {
	}

	@Override
	public void moveAnalog() {
	}

	@Override
	public void handleEvent(Event event) {
		if("A".equals(event.getComponent().toString())){
			actionButton();
		}
	}

}
