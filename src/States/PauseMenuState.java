package States;


public class PauseMenuState implements GameState {

	@Override
	public void useActionButton(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useBrakeButton(StateContext stateContext, GameState currentState) {
		// Back out of pause menu
		stateContext.setState(currentState);

	}

	@Override
	public void useWeapon(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause(StateContext stateContext, GameState currentState) {
		// Effectively unpauses the game
		stateContext.setState(currentState);

	}

	@Override
	public void moveUp(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveDown(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveLeft(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveAnalog(StateContext stateContext, GameState currentState) {
		// TODO Auto-generated method stub

	}

}
