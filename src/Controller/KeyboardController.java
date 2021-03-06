package Controller;

import net.java.games.input.Event;
import Graphics.RenderMaster;
import States.StateContext;

public class KeyboardController implements GameController {
	
	// Don't change arrow keys. The other 4 values can be moved to wherever we deem best
	private static final String UP_KEY = "Up";
	private static final String DOWN_KEY = "Down";
	private static final String LEFT_KEY = "Left";
	private static final String RIGHT_KEY = "Right";
	private static final String ACTION_KEY = " "; // Space bar
	private static final String BACK_KEY = "Z"; 
	private static final String WEAPON_KEY = "X";
	private static final String PAUSE_KEY = "Escape";
	
	private int upValue;
	private int downValue;
	private int leftValue;
	private int rightValue;
	private int actionValue;
	private int backValue;
	private int weaponValue;
	private int pauseValue;
	
	private int id;
	
	public KeyboardController(int id){
		this.id = id;
		
		upValue		= 0;
		downValue	= 0;
		leftValue	= 0;
		rightValue	= 0;
		actionValue	= 0;
		backValue	= 0;
		weaponValue	= 0;
		pauseValue	= 0;
	}
	
	@Override
	public void handleEvent(Event event, StateContext stateContext, RenderMaster renderMaster) {
		float eventValue = event.getValue();
		int intEventValue = (int)eventValue;
		String eventComponentString = event.getComponent().toString();
		if(ACTION_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.useActionButton(stateContext, getId());
			}
			actionValue = intEventValue;
		} else if(BACK_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.useBackButton(stateContext, getId());
			}
			backValue = intEventValue;
		} else if(WEAPON_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.useWeapon(stateContext, getId());
			}
			weaponValue = intEventValue;
		} else if(PAUSE_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.pause(stateContext, getId());
			}
			pauseValue = intEventValue;
		} else if(UP_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.moveUp(stateContext, getId());
			}
			upValue = intEventValue;
		} else if(DOWN_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.moveDown(stateContext, getId());
			}
			downValue = intEventValue;
		} else if(LEFT_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.moveLeft(stateContext, getId());
			}
			leftValue = intEventValue;
		} else if(RIGHT_KEY.equals(eventComponentString)){
			if (intEventValue == 1) {
				stateContext.moveRight(stateContext, getId());
			}
			rightValue = intEventValue;
		}
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public int getActionValue() {
		return actionValue;
	}

	@Override
	public int getBackValue() {
		return backValue;
	}

	@Override
	public int getWeaponValue() {
		return weaponValue;
	}

	@Override
	public int getPauseValue() {
		return pauseValue;
	}
	
	@Override
	public float getLeftRightValue() {
		if(rightValue == leftValue) return 0;
		return (rightValue > leftValue) ? rightValue : leftValue * -1;
	}
	
	@Override
	public float getUpDownValue() {
		if(upValue == downValue) return 0;
		return (upValue > downValue) ? upValue : downValue * -1;
	}
	
	@Override
	public float getForwardBackValue() {
		return getUpDownValue();
	}
	
	@Override
	public float getJumpValue(){
		return actionValue;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof KeyboardController)){
			return false;
		}
		
		KeyboardController otherController = (KeyboardController)other;
		return (otherController.getId() == this.getId());
	}


}
