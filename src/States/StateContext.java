package States;

import Graphics.RenderMaster;

public class StateContext {
	
	protected static final GameState RACING_STATE 		= new RacingState();
	protected static final GameState PAUSE_MENU_STATE 	= new PauseMenuState();
	
	private GameState gameState;
	private int lockedControllerId; // ID of Controller with "lock" (For Pausing and Such). 0 For Nobody
	
	public StateContext() {
		setState(new RacingState());
		setLockedControllerId(0);
	}
	
	public GameState getState(){
		return gameState;
	}
	
	public void setState(final GameState newState){
		gameState = newState;
	}
	
	public int getLockedControllerId(){
		return lockedControllerId;
	}
	
	public void setLockedControllerId(int newLockedControllerId){
		lockedControllerId = newLockedControllerId;
	}

	public void useActionButton(StateContext stateContext, RenderMaster renderMaster) {
		gameState.useActionButton(stateContext, gameState, renderMaster);
	}

	public void useBackButton(StateContext stateContext, RenderMaster renderMaster) {
		gameState.useBackButton(stateContext, gameState, renderMaster);
	}

	public void useWeapon(StateContext stateContext, RenderMaster renderMaster) {
		gameState.useWeapon(stateContext, gameState, renderMaster);
	}

	public void pause(StateContext stateContext, RenderMaster renderMaster) {
		gameState.pause(stateContext, gameState, renderMaster);
	}

	public void moveUp(StateContext stateContext, RenderMaster renderMaster) {
		gameState.moveUp(stateContext, gameState, renderMaster);
	}

	public void moveDown(StateContext stateContext, RenderMaster renderMaster) {
		gameState.moveDown(stateContext, gameState, renderMaster);
	}

	public void moveLeft(StateContext stateContext, RenderMaster renderMaster) {
		gameState.moveLeft(stateContext, gameState, renderMaster);
	}

	public void moveRight(StateContext stateContext, RenderMaster renderMaster) {
		gameState.moveRight(stateContext, gameState, renderMaster);
	}
}
