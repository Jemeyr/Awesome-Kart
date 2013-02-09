package States;


/**
 * This is the state for when the player is actually racing.
 * These functions will delegate elsewhere to do the actual work.
 *
 */
public class RacingState implements GameState {

	@Override
	public void useActionButton(StateContext stateContext, GameState currentState) {
		// Delegate to Player/Kart, call some accelerate function on it
		
	}

	@Override
	public void useBrakeButton(StateContext stateContext, GameState currentState) {
		// Delegate to player/kart, call a brake function.
	}

	@Override
	public void useWeapon(StateContext stateContext, GameState currentState) {
		// Delegate to player/kart, call use weapon.
		
	}

	@Override
	public void pause(StateContext stateContext, GameState currentState) {
		stateContext.setState(new PauseMenuState());
		
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
