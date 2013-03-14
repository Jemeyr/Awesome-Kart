package Controller;

import Game.DigitalRightsManagement;
import States.StateContext;
import World.Player;
import net.java.games.input.*;

/**
 * A test class for working with just controller related stuff
 *
 */
public class ControllerMain {

		public static void main(String[] args) throws Exception {
			ControllerManager cm = new ControllerManager();
			GameController gameController = cm.addController(ControllerType.KEYBOARD);
			//GameController xboxController = cm.addController(ControllerType.XBOX);
			EventManager em = new EventManager();
			StateContext stateContext = new StateContext();
			Player player = new Player(gameController, null, null);
			for(;;){
				cm.poll();
				em.handleEvents(cm.getEvents(), stateContext, null);
			}
		}
}
