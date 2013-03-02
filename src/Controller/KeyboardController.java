package Controller;

import net.java.games.input.Event;
import States.StateContext;

public class KeyboardController implements GameController {
	
	private int id;
	
	public KeyboardController(int id){
		this.id = id;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBrakeValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWeaponValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPauseValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public float getLeftRightValue(){
		return 0;
	}

}
