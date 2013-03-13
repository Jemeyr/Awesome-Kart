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
	
	public void resetLock() {
		setLockedControllerId(0);
	}

	public void useActionButton(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.useActionButton(stateContext, gameState, renderMaster, invokingId);
	}

	public void useBackButton(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.useBackButton(stateContext, gameState, renderMaster, invokingId);
	}

	public void useWeapon(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.useWeapon(stateContext, gameState, renderMaster, invokingId);
	}

	public void pause(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.pause(stateContext, gameState, renderMaster, invokingId);
	}

	public void moveUp(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.moveUp(stateContext, gameState, renderMaster, invokingId);
	}

	public void moveDown(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.moveDown(stateContext, gameState, renderMaster, invokingId);
	}

	public void moveLeft(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.moveLeft(stateContext, gameState, renderMaster, invokingId);
	}

	public void moveRight(StateContext stateContext, RenderMaster renderMaster, int invokingId) {
		gameState.moveRight(stateContext, gameState, renderMaster, invokingId);
	}
}
