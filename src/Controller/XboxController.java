package Controller;

import States.StateContext;
import net.java.games.input.Event;

public class XboxController implements GameController {
	
	private static final String START_BUTTON 		= "Unknown";
	private static final String RIGHT_THUMB_BUTTON 	= "Right Thumb";
	private static final String B_BUTTON 			= "B";
	private static final String A_BUTTON 			= "A";

	@Override
	public void actionButton() {
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
	public void handleEvent(Event event, StateContext stateContext) {
		if(event.getValue() > 0){
			if(A_BUTTON.equals(event.getComponent().toString())){
				stateContext.useActionButton(stateContext);
			}
			else if(B_BUTTON.equals(event.getComponent().toString())){
				stateContext.useBackButton(stateContext);
			}
			else if(RIGHT_THUMB_BUTTON.equals(event.getComponent().toString())){
				stateContext.useWeapon(stateContext);
			}
			else if(START_BUTTON.equals(event.getComponent().toString())){
				stateContext.pause(stateContext);
			}
		}
	}

}
