package Controller;

import Graphics.RenderMaster;
import States.StateContext;
import net.java.games.input.Event;

public interface GameController {
	
	public void handleEvent(Event event, StateContext stateContext, RenderMaster renderMaster);
	
	public int getId();					// Unique id of the controller
	public int getActionValue();		// Action button, like go, select, etc..
	public int getBackValue();			// Back button which will also be brake/reverse
	public int getWeaponValue();		// Button which triggers weapon usage
	public int getPauseValue();			// Button which pauses the game
	public float getLeftRightValue();	// Determines their steering position in terms of x axis
										// Note: From -2 to to 2, as keyboard has value 2.0 for held key, 1.0 for pressed
	public float getUpDownValue();		// Determines up/down, probably just used for selecting in menus and shit
										// Same as above. 2 is up, -2 is down
}
