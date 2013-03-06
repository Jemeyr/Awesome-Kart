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
			int lockedControllerId = stateContext.getLockedControllerId();
			for(GameEvent gameEvent : gameEvents){
				if(lockedControllerId == 0 || lockedControllerId == gameEvent.getGameController().getId()){
					gameEvent.getGameController().handleEvent(gameEvent.getEvent(), stateContext, renderMaster);
				}
			}
		}
}
