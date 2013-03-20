package States;

import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundMaster;

public class StateContext {
	
	protected static final GameState RACING_STATE 		= new RacingState();
	protected static final GameState PAUSE_MENU_STATE 	= new PauseMenuState();
	private static final int DEFAULT_CONTROLLER_LOCK 	= 0;			
	
	private RenderMaster renderMaster;
	private SoundMaster soundMaster;
	private GameState gameState;
	private int lockedControllerId; // ID of Controller with "lock" (For Pausing and Such). 0 For Nobody
	
	public StateContext() {
		//renderMaster = RenderMasterFactory.getRenderMaster();
		soundMaster = new SoundMaster();
		
		setState(RACING_STATE);
		setLockedControllerId(DEFAULT_CONTROLLER_LOCK);
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
	
	public void resetControllerLock() {
		setLockedControllerId(DEFAULT_CONTROLLER_LOCK);
	}

	public void useActionButton(StateContext stateContext, int invokingId) {
		gameState.useActionButton(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void useBackButton(StateContext stateContext, int invokingId) {
		gameState.useBackButton(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void useWeapon(StateContext stateContext, int invokingId) {
		gameState.useWeapon(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void pause(StateContext stateContext, int invokingId) {
		gameState.pause(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveUp(StateContext stateContext, int invokingId) {
		gameState.moveUp(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveDown(StateContext stateContext, int invokingId) {
		gameState.moveDown(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveLeft(StateContext stateContext, int invokingId) {
		gameState.moveLeft(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveRight(StateContext stateContext, int invokingId) {
		gameState.moveRight(stateContext, renderMaster, soundMaster, invokingId);
	}
}
