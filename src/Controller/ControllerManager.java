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

	private HashMap<Controller, GameController> gameControllers;
	private HashMap<Event, GameController> eventMappings;
	
	public ControllerManager() {
		this.gameControllers = new HashMap<Controller, GameController>();
	}
	
	public GameController addController() {
		if(ControllerEnvironment.getDefaultEnvironment().getControllers().length > gameControllers.size()){
			for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()){
				if(gameControllers.get(c) == null){
					GameController gameController = new XboxController();
					gameControllers.put(c, gameController);
					return gameController;
				}
			}
		}
		return null;
	}
	
	public void poll(){
		eventMappings = new HashMap<Event, GameController>();
		for(Map.Entry<Controller, GameController> entry : getControllersMap().entrySet()){
			Controller c = entry.getKey();
			c.poll();
			EventQueue eq = c.getEventQueue();
			Event event = new Event();
			while(eq.getNextEvent(event)){
				//System.out.println("event name " + event.getComponent() + " event value " + event.getValue());
				eventMappings.put(event, entry.getValue());
			}
		}
	}
	
	public HashMap<Event, GameController> getEvents(){
		return eventMappings;
	}
	
	public HashMap<Controller, GameController> getControllersMap(){
		return gameControllers;
	}
}
