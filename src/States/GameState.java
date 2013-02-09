package States;

public interface GameState {
	
	void useActionButton(final StateContext stateContext, final GameState currentState);
	void useBrakeButton(final StateContext stateContext, final GameState currentState);
	void useWeapon(final StateContext stateContext, final GameState currentState);
	void pause(final StateContext stateContext, final GameState currentState);
	void moveUp(final StateContext stateContext, final GameState currentState);
	void moveDown(final StateContext stateContext, final GameState currentState);
	void moveLeft(final StateContext stateContext, final GameState currentState);
	void moveRight(final StateContext stateContext, final GameState currentState);
	void moveAnalog(final StateContext stateContext, final GameState currentState); // For Xbox Controllers
	
}
