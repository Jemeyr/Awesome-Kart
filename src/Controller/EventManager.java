package Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.java.games.input.Event;

import Graphics.RenderMaster;
import States.StateContext;

public class EventManager {
	
		public EventManager(){
		}
		
		public void handleEvents(HashSet<GameEvent> gameEvents, StateContext stateContext, RenderMaster renderMaster){
			for(GameEvent gameEvent : gameEvents){
				gameEvent.getGameController().handleEvent(gameEvent.getEvent(), stateContext, renderMaster);
			}
		}
}
