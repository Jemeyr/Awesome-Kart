package States;

import Graphics.RenderMaster;
import Sound.SoundMaster;


public class PauseMenuState implements GameState {

	public static final boolean DEBUG = true;
	
	@Override
	public void useActionButton(StateContext stateContext, GameState currentState, RenderMaster randerMaster, SoundMaster soundMaster, int invokingId) {
		// Select the specific item, go to state based on where it is selected
		if(DEBUG) System.out.println(invokingId + ": The paused A button. Back to race");
		stateContext.setState(StateContext.RACING_STATE);
		stateContext.resetControllerLock();
	}

	@Override
	public void useBackButton(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		// Back out of pause menu
		if(DEBUG) System.out.println(invokingId + ": Returning to Game");
		stateContext.setState(StateContext.RACING_STATE);
		stateContext.resetControllerLock();
	}

	@Override
	public void useWeapon(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		// Do Nothing probably
		if(DEBUG) System.out.println(invokingId + ": I do nothing");
	}

	@Override
	public void pause(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		// Effectively unpauses the game
		if(DEBUG) System.out.println(invokingId + ": In the Pause Menu, going back to Race");
		stateContext.setState(StateContext.RACING_STATE);
		stateContext.resetControllerLock();
	}

	@Override
	public void moveUp(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Moving Up in menu");
		// Select the next up box, wraps around to bottom?
		// This state should have some idea of what the "active item" in the menu
		// so that it can act accordingly on it

	}

	@Override
	public void moveDown(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Moving Down in menu");
		// Select the next box down, wraps around to top?
	}

	@Override
	public void moveLeft(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Moving Left in menu, but does nothing");
		// Do nothing probably
	}

	@Override
	public void moveRight(StateContext stateContext, GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Moving Right in menu, but does nothing");
		// Do nothing probably

	}

}
