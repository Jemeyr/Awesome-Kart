package States;

import Graphics.RenderMaster;


public class PauseMenuState implements GameState {

	@Override
	public void useActionButton(StateContext stateContext, GameState currentState, RenderMaster randerMaster, int invokingId) {
		// Select the specific item, go to state based on where it is selected
		System.out.println("The paused A button. Back to race");
		stateContext.setState(StateContext.RACING_STATE);
		stateContext.resetLock();
	}

	@Override
	public void useBackButton(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Back out of pause menu
		System.out.println("Returning to Game");
		stateContext.setState(StateContext.RACING_STATE);
		stateContext.resetLock();
	}

	@Override
	public void useWeapon(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Do Nothing probably
		System.out.println("I do nothing");
	}

	@Override
	public void pause(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Effectively unpauses the game
		System.out.println("In the Pause Menu, going back to Race");
		stateContext.setState(StateContext.RACING_STATE);
		stateContext.resetLock();
	}

	@Override
	public void moveUp(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Select the next up box, wraps around to bottom?
		// This state should have some idea of what the "active item" in the menu
		// so that it can act accordingly on it

	}

	@Override
	public void moveDown(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Select the next box down, wraps around to top?
	}

	@Override
	public void moveLeft(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Do nothing probably
	}

	@Override
	public void moveRight(StateContext stateContext, GameState currentState, RenderMaster renderMaster, int invokingId) {
		// Do nothing probably

	}

}
