package States;

import Graphics.RenderMaster;
import Sound.SoundMaster;


/**
 * This is the state for when the player is actually racing.
 * These functions will delegate elsewhere to do the actual work.
 *
 */
public class RacingState implements GameState {
	
	public static final boolean DEBUG = true;
	
	@Override
	public void useActionButton(StateContext stateContext, RenderMaster randerMaster, SoundMaster soundMaster, int invokingId) {
		// Delegate to Player/Kart, call some accelerate function on it
		if(DEBUG) System.out.println(invokingId + ": Accelerating in this direction.");
	}

	@Override
	public void useBackButton(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		// Delegate to player/kart, call a brake function.
		if(DEBUG) System.out.println(invokingId + ": Braking!!!");
	}

	@Override
	public void useWeapon(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		// Delegate to player/kart, call use weapon.
		if(DEBUG) System.out.println(invokingId + ": Fire Homing Torpedoes!");
	}

	@Override
	public void pause(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": About to pause the race.");
		stateContext.setState(StateContext.PAUSE_MENU_STATE);
		stateContext.setLockedControllerId(invokingId);
	}

	@Override
	public void moveUp(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Moving up");
	}

	@Override
	public void moveDown(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Moving down");
	}

	@Override
	public void moveLeft(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Turning left!");
	}

	@Override
	public void moveRight(StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId) {
		if(DEBUG) System.out.println(invokingId + ": Turning Right!");
	}
	
}
