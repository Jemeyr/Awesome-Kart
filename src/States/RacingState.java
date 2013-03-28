package States;

import java.util.ArrayList;
import java.util.List;

import Graphics.Camera;
import Graphics.RenderMaster;
import Sound.ListenerComponent;
import Sound.SoundEmitter;
import Sound.SoundMaster;
import World.Player;
import World.World;


/**
 * This is the state for when the player is actually racing.
 * These functions will delegate elsewhere to do the actual work.
 *
 */
public class RacingState implements GameState {
	
	public static final boolean DEBUG = false;
	
	private RenderMaster 								renderMaster;
	private SoundMaster									soundMaster;
	private World										world;
	private List<Camera>								cameras;
	private int											elec360power;
		
	public RacingState(RenderMaster renderMaster, SoundMaster soundMaster, List<Player> playerList){
		this.renderMaster 	= renderMaster;
		this.soundMaster	= soundMaster;
		cameras 			= new ArrayList<Camera>();


		
		elec360power		= 0;
		
		this.world = /*A whole*/ new World(renderMaster, playerList);// A new fantastic point of view/No one to tell us no or where to go/Or say we're only dreaming
		
		initialiseState();
		
		for(Player player : playerList)
		{
			player.setWorld(this.world);
		}
		
	}
	
	@Override
	public void initialiseState() {
		ListenerComponent listenerComponent = null;

		
		
		
		// Add and start music
		SoundEmitter musicComponent=this.soundMaster.getSoundComponent("assets/sound/alarma.wav", true); 
		//musicComponent.playSound();
	}
	
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
		stateContext.getPlayerList().get(invokingId).useWeapon();
		
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

	@Override
	public void execute(List<Player> playerList) {
		for(Player player : playerList){
			player.update();
		}
		
		this.world.update();
		
		for(Player player: playerList){
			player.updateCamera();
		}
		
		renderMaster.draw();
	}
	
}
