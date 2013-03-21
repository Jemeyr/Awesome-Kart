package Controller;

import Graphics.RenderMaster;
import States.StateContext;
import net.java.games.input.Event;

public class XboxController implements GameController {
	
	private static final String START_BUTTON 		= "Unknown";
	private static final String RIGHT_THUMB_BUTTON 	= "Right Thumb";
	private static final String B_BUTTON 			= "B";
	private static final String A_BUTTON 			= "A";
	private static final String X_BUTTON			= "X";
	private static final String JOYSTICK_X_DIR		= "x";
	private static final String JOYSTICK_Y_DIR		= "y";
	
	private static final int 	MULTIPLIER			= 2; // Keyboard has 1 as pressed, 2 as held, this maps it from Xbox
	
	private int id;
	private int aButtonValue;
	private int bButtonValue;
	private int xButtonValue;
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
		//System.out.println("My event is '" + event.getComponent().getName() + "' and value is '" + event.getValue() + "'");
		
		int intEventValue = (int)eventValue;
		String eventComponentString = event.getComponent().toString();
		if(A_BUTTON.equals(eventComponentString)){
			if(eventValue > 0.01f){
				stateContext.useActionButton(stateContext, getId());
			}
			aButtonValue = intEventValue * MULTIPLIER;
		}
		else if(B_BUTTON.equals(eventComponentString)){
			if(eventValue > 0.01f){
				stateContext.useBackButton(stateContext, getId());
			}
			bButtonValue = intEventValue * MULTIPLIER;
		}
		else if(X_BUTTON.equals(eventComponentString)){
			xButtonValue = intEventValue * MULTIPLIER;
		}
		else if(RIGHT_THUMB_BUTTON.equals(eventComponentString)){
			if(eventValue > 0.01f){
				stateContext.useWeapon(stateContext, getId());
			}
			weaponButtonValue = intEventValue * MULTIPLIER;
		}
		else if(START_BUTTON.equals(eventComponentString)){
			if(eventValue > 0.01f){
				stateContext.pause(stateContext, getId());
			} 
			pauseButtonValue = intEventValue * MULTIPLIER;
		}
		else if(JOYSTICK_X_DIR.equals(eventComponentString)){
			int multiplier = MULTIPLIER;
			if(eventValue < -0.25f){
				stateContext.moveLeft(stateContext, getId());
				multiplier *= -1;
			} else if (eventValue > 0.25f){
				stateContext.moveRight(stateContext, getId());
			}
			leftRightValue = eventValue * multiplier;
		}
		else if(JOYSTICK_Y_DIR.equals(eventComponentString)){
			int multiplier = MULTIPLIER;
			if(eventValue > 0.25f){
				stateContext.moveDown(stateContext, getId());
				multiplier *= -1;
			} else if(eventValue < -0.25f){
				stateContext.moveUp(stateContext, getId());
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
	public float getForwardBackValue() {
		if (aButtonValue == bButtonValue) return 0;
		return (aButtonValue - bButtonValue);
	}
	
	@Override
	public float getJumpValue(){
		return xButtonValue;
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
