package Controller;

import Game.DigitalRightsManagement;
import States.StateContext;
import net.java.games.input.*;

/**
 * A test class for working with just controller related stuff
 *
 */
public class ControllerMain {

		public static void main(String[] args) throws Exception {
			ControllerManager cm = new ControllerManager();
			GameController gameController = cm.addController(ControllerType.KEYBOARD);
			EventManager em = new EventManager();
			StateContext stateContext = new StateContext();
			for(;;){
				cm.poll();
				em.handleEvents(cm.getEvents(), stateContext, null);
			}
		}
}
