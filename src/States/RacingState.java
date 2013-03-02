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
		System.out.println("Accelerating in this direction.");
	}

	@Override
	public void useBackButton(StateContext stateContext, GameState currentState) {
		// Delegate to player/kart, call a brake function.
		System.out.println("Braking!!!");
	}

	@Override
	public void useWeapon(StateContext stateContext, GameState currentState) {
		// Delegate to player/kart, call use weapon.
		System.out.println("Fire Homing Torpedoes!");
	}

	@Override
	public void pause(StateContext stateContext, GameState currentState) {
		System.out.println("About to pause the race.");
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
		System.out.println("Turning left!");
	}

	@Override
	public void moveRight(StateContext stateContext, GameState currentState) {
		System.out.println("Turning Right!");
	}
	
}
