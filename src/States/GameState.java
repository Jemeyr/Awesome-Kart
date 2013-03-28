package States;

import java.util.List;

import Graphics.RenderMaster;
import Sound.SoundMaster;
import World.Player;

public interface GameState {
	
	void useActionButton(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void useBackButton(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void useWeapon(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void pause(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveUp(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveDown(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveLeft(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void moveRight(final StateContext stateContext, RenderMaster renderMaster, SoundMaster soundMaster, int invokingId);
	void reportLapCompleted(Player player);	
	void execute(List<Player> playerList); //Could be renamed, whatever
	void initialiseState(); //Call to initialise everything at the start of the state.
	
}
