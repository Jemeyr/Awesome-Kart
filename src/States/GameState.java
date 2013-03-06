package States;

import Graphics.RenderMaster;

public interface GameState {
	
	void useActionButton(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void useBackButton(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void useWeapon(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void pause(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void moveUp(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void moveDown(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void moveLeft(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	void moveRight(final StateContext stateContext, final GameState currentState, RenderMaster renderMaster);
	
}
