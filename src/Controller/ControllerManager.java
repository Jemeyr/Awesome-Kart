package Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import States.StateContext;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class ControllerManager {

	private HashMap<Controller, GameController> gameControllers;
	private HashSet<GameEvent> gameEvents;
	private int currentId;
	
	public ControllerManager() {
		this.gameControllers 	= new HashMap<Controller, GameController>();
		this.gameEvents 		= new HashSet<GameEvent>();
		currentId 				= 1;
	}
	
	public GameController addController(ControllerType controllerType) {
		GameController gameController = null;
		if(ControllerEnvironment.getDefaultEnvironment().getControllers().length > gameControllers.size()){
			for(Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()){
				if(gameControllers.get(c) == null && 
							c.getName().toLowerCase().contains(controllerType.getName())){
						switch(controllerType){
							case XBOX: 		gameController = new XboxController(currentId++); 		break;
							case KEYBOARD: 	gameController = new KeyboardController(currentId++); 	break;
						}
						if(gameController != null){
							gameControllers.put(c, gameController);
							return gameController;
						} else {
							System.out.println("No controllers found!!! Nyeaguh!");
						}
					}	
				}
			}
		return null;
	}
	
	public void poll(){
		gameEvents = new HashSet<GameEvent>();
		for(Map.Entry<Controller, GameController> entry : getControllersMap().entrySet()){
			Controller c = entry.getKey();
			c.poll();
			EventQueue eq = c.getEventQueue();
			Event event = new Event();
			while(eq.getNextEvent(event)){
				//System.out.println("event name bunkai " + event.getComponent() + " event value " + event.getValue());
				gameEvents.add(new GameEvent(event, entry.getValue()));
			}
		}
	}
	
	public HashSet<GameEvent> getEvents(){
		return gameEvents;
	}
	
	public HashMap<Controller, GameController> getControllersMap(){
		return gameControllers;
	}
}
