package States;

import Graphics.RenderMaster;
import Sound.SoundMaster;

public interface GameState {
	
	void useActionButton(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void useBackButton(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void useWeapon(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void pause(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveUp(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveDown(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveLeft(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveRight(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	
}
