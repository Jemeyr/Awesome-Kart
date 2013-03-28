package Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
		currentId 				= 0;
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
		gameEvents.clear();
		gameEvents = new HashSet<GameEvent>();
		for(Map.Entry<Controller, GameController> entry : getControllersMap().entrySet()){
			Controller c = entry.getKey();
			c.poll();
			EventQueue eq = c.getEventQueue();
			Event event = new Event();
			while(eq.getNextEvent(event)){
				Event e2 = new Event();
				e2.set(event);
				//System.out.println("event name bunkai '" + event.getComponent().getName() + "' event value " + event.getValue());
				GameEvent gameEvent = new GameEvent(e2, entry.getValue());
				gameEvents.add(gameEvent);
			}
		}
	}
	
	public HashSet<GameEvent> getEvents(){
		return new HashSet<GameEvent>(gameEvents);
	}
	
	public HashMap<Controller, GameController> getControllersMap(){
		return gameControllers;
	}
}
