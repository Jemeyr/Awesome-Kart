package Controller;

import java.util.HashSet;

import Graphics.RenderMaster;
import States.StateContext;

public class EventManager {
	
		public EventManager(){ }
		
		public void handleEvents(HashSet<GameEvent> gameEvents, StateContext stateContext, RenderMaster renderMaster){
			int lockedControllerId = stateContext.getLockedControllerId();
			//System.out.println("Game Events: "+ gameEvents.size());
			for(GameEvent gameEvent : gameEvents){
				//System.out.println(gameEvent.getEvent().getComponent().getName() + " " + gameEvent.getGameController().getId());
				if(lockedControllerId == 0 || lockedControllerId == gameEvent.getGameController().getId()){
					gameEvent.getGameController().handleEvent(gameEvent.getEvent(), stateContext, renderMaster);
				}
			}
			gameEvents.clear();
		}
}
