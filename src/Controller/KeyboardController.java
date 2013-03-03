package Controller;

import net.java.games.input.Event;
import States.StateContext;

public class KeyboardController implements GameController {
	
	private static final String UP_KEY = "Up";
	private static final String DOWN_KEY = "Down";
	private static final String LEFT_KEY = "Left";
	private static final String RIGHT_KEY = "Right";
	private static final String ACTION_KEY = " "; // Space bar
	private static final String BRAKE_KEY = "Z";
	private static final String WEAPON_KEY = "X";
	private static final String PAUSE_KEY = "Escape";
	
	private int upValue;
	private int downValue;
	private int leftValue;
	private int rightValue;
	private int actionValue;
	private int brakeValue;
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
		brakeValue	= 0;
		weaponValue	= 0;
		pauseValue	= 0;
	}
	
	@Override
	public void handleEvent(Event event, StateContext stateContext) {
		
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
	public int getBrakeValue() {
		return brakeValue;
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
		return (rightValue >= leftValue) ? rightValue : leftValue * -1;
	}
	
	@Override
	public float getUpDownValue() {
		return (upValue >= downValue) ? upValue : downValue * -1;
	}

}
