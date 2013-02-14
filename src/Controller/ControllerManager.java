package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import States.StateContext;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerManager {

	private StateContext stateContext;
	private HashMap<Controller, GameController> gameControllers;
	
	public ControllerManager() {
		this.stateContext = new StateContext();
		this.gameControllers = new HashMap<Controller, GameController>();
		addController();
	}
	
	public void addController() {
		if(ControllerEnvironment.getDefaultEnvironment().getControllers().length > gameControllers.size()){
			GameController newController = new XboxController();
			for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()){
				if(gameControllers.get(c) == null){
					gameControllers.put(c, newController);
				}
			}
		}
	}
	
	public void poll(){
		for(Map.Entry<Controller, GameController> entry : getControllersMap().entrySet()){
			Controller c = entry.getKey();
			c.poll();
			EventQueue eq = c.getEventQueue();
			Event event = new Event();
			if(eq.getNextEvent(event)){
				entry.getValue().handleEvent(event);
			}
		}
	}
	
	public HashMap<Controller, GameController> getControllersMap(){
		return gameControllers;
	}
}
