package Controller;

import java.util.HashMap;
import java.util.List;

import States.StateContext;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerManager {

	private StateContext stateContext;
	private HashMap<Integer, GameController> gameControllers;
	
	public ControllerManager() {
		this.stateContext = new StateContext();
		this.gameControllers = new HashMap<Integer, GameController>();
		addController();
	}
	
	public void addController() {
		if(ControllerEnvironment.getDefaultEnvironment().getControllers().length > gameControllers.size()){
			GameController newController = new XboxController();
			for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()){
				if(gameControllers.get(c.getPortNumber()) == null){
					gameControllers.put(c.getPortNumber(), newController);
				}
			}
		}
	}
	
	public void poll(){
		for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()){
			c.poll();
			EventQueue eq = c.getEventQueue();
			Event event = new Event();
			if(eq.getNextEvent(event)){
				gameControllers.get(c.getPortNumber()).handleEvent(event);
			}
		}
	}
}
