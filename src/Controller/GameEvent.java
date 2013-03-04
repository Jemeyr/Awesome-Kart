package Controller;

import net.java.games.input.Event;

public class GameEvent {

	private Event 			event;
	private GameController 	gameController;
	
	public GameEvent(Event event, GameController gameController){
		this.event 			= event;
		this.gameController = gameController;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null || !(other instanceof GameEvent)){
			return false;
		}
		
		GameEvent otherEvent = (GameEvent)other;
		
		return (otherEvent.getGameController() == this.getGameController() &&
				otherEvent.getEvent() == this.getEvent());
	}
}
