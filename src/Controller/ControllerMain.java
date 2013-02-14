package Controller;

import net.java.games.input.*;

/**
 * A test class for working with just controller related stuff
 *
 */
public class ControllerMain {

		public static void main(String[] args) throws Exception {
			for(;;){
				for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()){
					c.poll();
					EventQueue eq = c.getEventQueue();
					Event event = new Event();
					if(eq.getNextEvent(event)){
						System.out.println(event.getComponent());
						System.out.println(c.getPortNumber());
					}
				}
			}
		}
}
