package Controller;

import Graphics.RenderMaster;
import States.StateContext;
import net.java.games.input.Event;

public class XboxController implements GameController {
	
	private static final String START_BUTTON 		= "Unknown";
	private static final String RIGHT_THUMB_BUTTON 	= "Right Thumb";
	private static final String B_BUTTON 			= "B";
	private static final String A_BUTTON 			= "A";
	private static final String JOYSTICK_X_DIR		= "x";
	private static final String JOYSTICK_Y_DIR		= "y";
	
	private static final int 	MULTIPLIER			= 2; // Keyboard has 1 as pressed, 2 as held, this maps it from Xbox
	
	private int id;
	private int aButtonValue;
	private int bButtonValue;
	private int weaponButtonValue;
	private int pauseButtonValue;
	private float leftRightValue;
	private float upDownValue;
	
	public XboxController(int id) {
		aButtonValue = 0;
		bButtonValue = 0;
		weaponButtonValue = 0;
		pauseButtonValue = 0;
		leftRightValue = 0;
		upDownValue = 0;
		this.id = id;
	}

	@Override
	public void handleEvent(Event event, StateContext stateContext, RenderMaster renderMaster) {
		float eventValue = event.getValue();
		int intEventValue = (int)eventValue;
		String eventComponentString = event.getComponent().toString();
		if(A_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.useActionButton(stateContext, renderMaster, getId());
			}
			aButtonValue = intEventValue * MULTIPLIER;
		}
		else if(B_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.useBackButton(stateContext, renderMaster, getId());
			}
			bButtonValue = intEventValue * MULTIPLIER;
		}
		else if(RIGHT_THUMB_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.useWeapon(stateContext, renderMaster, getId());
			}
			weaponButtonValue = intEventValue * MULTIPLIER;
		}
		else if(START_BUTTON.equals(eventComponentString)){
			if(intEventValue > 0){
				stateContext.pause(stateContext, renderMaster, getId());
			} 
			pauseButtonValue = intEventValue * MULTIPLIER;
		}
		else if(JOYSTICK_X_DIR.equals(eventComponentString)){
			int multiplier = MULTIPLIER;
			if(eventValue < 0){
				stateContext.moveLeft(stateContext, renderMaster, getId());
				multiplier *= -1;
			} else if (eventValue > 0){
				stateContext.moveRight(stateContext, renderMaster, getId());
			}
			leftRightValue = eventValue * multiplier;
		}
		else if(JOYSTICK_Y_DIR.equals(eventComponentString)){
			int multiplier = MULTIPLIER;
			if(eventValue < 0){
				stateContext.moveDown(stateContext, renderMaster, getId());
				multiplier *= -1;
			} else if(eventValue > 0){
				stateContext.moveUp(stateContext, renderMaster, getId());
			}
			upDownValue = eventValue * multiplier;
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
	public int getBackValue() {
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
	
	@Override
	public float getUpDownValue() {
		return upDownValue;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof XboxController)){
			return false;
		}
		
		XboxController otherController = (XboxController)other;
		return (otherController.getId() == this.getId());
	}

}
