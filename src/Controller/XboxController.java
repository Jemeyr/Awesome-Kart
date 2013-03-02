package Controller;

import States.StateContext;
import net.java.games.input.Event;

public class XboxController implements GameController {
	
	private static final String START_BUTTON 		= "Unknown";
	private static final String RIGHT_THUMB_BUTTON 	= "Right Thumb";
	private static final String B_BUTTON 			= "B";
	private static final String A_BUTTON 			= "A";
	private static final String JOYSTICK_X_DIR		= "x";
	
	private int id;
	private int aButtonValue;
	private int bButtonValue;
	private int weaponButtonValue;
	private int pauseButtonValue;
	private float leftRightValue;
	
	public XboxController(int id) {
		aButtonValue = 0;
		bButtonValue = 0;
		weaponButtonValue = 0;
		pauseButtonValue = 0;
		leftRightValue = 0;
		this.id = id;
	}

	@Override
	public void handleEvent(Event event, StateContext stateContext) {
		float eventValue = event.getValue();
		int intEventValue = (int)eventValue;
		String eventComponentString = event.getComponent().toString();
		if(A_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.useActionButton(stateContext);
			}
			aButtonValue = intEventValue;
		}
		else if(B_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.useBackButton(stateContext);
			}
			bButtonValue = intEventValue;
		}
		else if(RIGHT_THUMB_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.useWeapon(stateContext);
			}
			weaponButtonValue = intEventValue;
		}
		else if(START_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.pause(stateContext);
			}
			pauseButtonValue = intEventValue;
		}
		else if(JOYSTICK_X_DIR.equals(eventComponentString)){
			if(eventValue < 0){
				stateContext.moveLeft(stateContext);
			} else if (eventValue > 0){
				stateContext.moveRight(stateContext);
			}
			leftRightValue = eventValue * 2;
		}
	}
	
	@Override
	public int getId(){
		return id;
	}

	@Override
	public int getActionValue() {
		return aButtonValue;
	}

	@Override
	public int getBrakeValue() {
		return bButtonValue;
	}

	@Override
	public int getWeaponValue() {
		return weaponButtonValue;
	}

	@Override
	public int getPauseValue() {
		return pauseButtonValue;
	}
	
	@Override
	public float getLeftRightValue(){
		return leftRightValue;
	}

}
