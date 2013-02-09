package States;

public class StateContext {
	
	private GameState currentState;
	
	public StateContext() {
		setState(new RacingState());
	}
	
	public void setState(final GameState newState){
		currentState = newState;
	}

	public void useActionButton(StateContext stateContext, GameState currentState) {
		currentState.useActionButton(stateContext, currentState);
	}

	public void useBrakeButton(StateContext stateContext, GameState currentState) {
		currentState.useBrakeButton(stateContext, currentState);
	}

	public void useWeapon(StateContext stateContext, GameState currentState) {
		currentState.useWeapon(stateContext, currentState);
	}

	public void pause(StateContext stateContext, GameState currentState) {
		currentState.pause(stateContext, currentState);
	}

	public void moveUp(StateContext stateContext, GameState currentState) {
		currentState.moveUp(stateContext, currentState);
	}

	public void moveDown(StateContext stateContext, GameState currentState) {
		currentState.moveDown(stateContext, currentState);
	}

	public void moveLeft(StateContext stateContext, GameState currentState) {
		currentState.moveLeft(stateContext, currentState);
	}

	public void moveRight(StateContext stateContext, GameState currentState) {
		currentState.moveRight(stateContext, currentState);
	}

	public void moveAnalog(StateContext stateContext, GameState currentState) {
		currentState.moveAnalog(stateContext, currentState);
	}
}
