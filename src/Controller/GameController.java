package Controller;

import States.StateContext;
import net.java.games.input.Event;

public interface GameController {
	
	public void handleEvent(Event event, StateContext stateContext);
	
	public int getId();
	public int getActionValue();
	public int getBrakeValue();
	public int getWeaponValue();
	public int getPauseValue();
	
}
