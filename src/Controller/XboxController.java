package Controller;

import States.StateContext;
import net.java.games.input.Event;

public class XboxController implements GameController {
	
	private static final String START_BUTTON 		= "Unknown";
	private static final String RIGHT_THUMB_BUTTON 	= "Right Thumb";
	private static final String B_BUTTON 			= "B";
	private static final String A_BUTTON 			= "A";
	
	private int id;
	private int aButtonValue;
	private int bButtonValue;
	private int weaponButtonValue;
	private int pauseButtonValue;
	
	public XboxController() {
		aButtonValue = 0;
		bButtonValue = 0;
		weaponButtonValue = 0;
		pauseButtonValue = 0;
	}

	@Override
	public void handleEvent(Event event, StateContext stateContext) {
		float eventValue = event.getValue();
		int intEventValue = (int)eventValue;
		if(A_BUTTON.equals(event.getComponent().toString())){
			if(intEventValue > 0){
				stateContext.useActionButton(stateContext);
			}
			aButtonValue = intEventValue;
		}
		else if(B_BUTTON.equals(event.getComponent().toString())){
			if(intEventValue > 0){
				stateContext.useBackButton(stateContext);
			}
			bButtonValue = intEventValue;
		}
		else if(RIGHT_THUMB_BUTTON.equals(event.getComponent().toString())){
			if(intEventValue > 0){
				stateContext.useWeapon(stateContext);
			}
			weaponButtonValue = intEventValue;
		}
		else if(START_BUTTON.equals(event.getComponent().toString())){
			if(intEventValue > 0){
				stateContext.pause(stateContext);
			}
			pauseButtonValue = intEventValue;
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

}
