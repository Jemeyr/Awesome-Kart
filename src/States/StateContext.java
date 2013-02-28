package States;

public class StateContext {
	
	private GameState gameState;
	
	public StateContext() {
		setState(new RacingState());
	}
	
	public GameState getState(){
		return gameState;
	}
	
	public void setState(final GameState newState){
		gameState = newState;
	}

	public void useActionButton(StateContext stateContext) {
		gameState.useActionButton(stateContext, gameState);
	}

	public void useBackButton(StateContext stateContext) {
		gameState.useBackButton(stateContext, gameState);
	}

	public void useWeapon(StateContext stateContext) {
		gameState.useWeapon(stateContext, gameState);
	}

	public void pause(StateContext stateContext) {
		gameState.pause(stateContext, gameState);
	}

	public void moveUp(StateContext stateContext) {
		gameState.moveUp(stateContext, gameState);
	}

	public void moveDown(StateContext stateContext) {
		gameState.moveDown(stateContext, gameState);
	}

	public void moveLeft(StateContext stateContext) {
		gameState.moveLeft(stateContext, gameState);
	}

	public void moveRight(StateContext stateContext) {
		gameState.moveRight(stateContext, gameState);
	}

	public void moveAnalog(StateContext stateContext) {
		gameState.moveAnalog(stateContext, gameState);
	}
}
