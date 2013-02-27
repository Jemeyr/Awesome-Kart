package Controller;

import java.util.HashMap;
import java.util.Map;

import net.java.games.input.Event;

import States.StateContext;

public class EventManager {
	
		public EventManager(){
		}
		
		public void handleEvents(HashMap<Event, GameController> eventMappings, StateContext stateContext){
			for(Map.Entry<Event, GameController> entry : eventMappings.entrySet()){
				entry.getValue().handleEvent(entry.getKey(), stateContext);
			}
		}
}
