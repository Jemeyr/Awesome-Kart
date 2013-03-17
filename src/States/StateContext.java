package States;

import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundMaster;

public class StateContext {
	
	protected static final GameState RACING_STATE 		= new RacingState();
	protected static final GameState PAUSE_MENU_STATE 	= new PauseMenuState();
	
	private RenderMaster renderMaster;
	private SoundMaster soundMaster;
	private GameState gameState;
	private int lockedControllerId; // ID of Controller with "lock" (For Pausing and Such). 0 For Nobody
	
	public StateContext() {
		renderMaster = RenderMasterFactory.getRenderMaster();
		soundMaster = new SoundMaster();
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

	public void useActionButton(StateContext stateContext, int invokingId) {
		gameState.useActionButton(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void useBackButton(StateContext stateContext, int invokingId) {
		gameState.useBackButton(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void useWeapon(StateContext stateContext, int invokingId) {
		gameState.useWeapon(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void pause(StateContext stateContext, int invokingId) {
		gameState.pause(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void moveUp(StateContext stateContext, int invokingId) {
		gameState.moveUp(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void moveDown(StateContext stateContext, int invokingId) {
		gameState.moveDown(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void moveLeft(StateContext stateContext, int invokingId) {
		gameState.moveLeft(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}

	public void moveRight(StateContext stateContext, int invokingId) {
		gameState.moveRight(stateContext, gameState, renderMaster, soundMaster, invokingId);
	}
}
