package States;


public class PauseMenuState implements GameState {

	@Override
	public void useActionButton(StateContext stateContext, GameState currentState) {
		// Select the specific item, go to state based on where it is selected
	}

	@Override
	public void useBackButton(StateContext stateContext, GameState currentState) {
		// Back out of pause menu
		stateContext.setState(currentState);
	}

	@Override
	public void useWeapon(StateContext stateContext, GameState currentState) {
		// Do Nothing probably
	}

	@Override
	public void pause(StateContext stateContext, GameState currentState) {
		// Effectively unpauses the game
		System.out.println("In the Pause Menu, going back to Race");
		stateContext.setState(currentState);
	}

	@Override
	public void moveUp(StateContext stateContext, GameState currentState) {
		// Select the next up box, wraps around to bottom?

	}

	@Override
	public void moveDown(StateContext stateContext, GameState currentState) {
		// Select the next box down, wraps around to top?
	}

	@Override
	public void moveLeft(StateContext stateContext, GameState currentState) {
		// Do nothing probably
	}

	@Override
	public void moveRight(StateContext stateContext, GameState currentState) {
		// Do nothing probably

	}

	@Override
	public void moveAnalog(StateContext stateContext, GameState currentState) {
		// Would go up or down based on direction
	}

}
