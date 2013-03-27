package States;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;
import Sound.ListenerComponent;
import Sound.SoundEmitter;
import Sound.SoundMaster;
import World.Kart;
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
		
		initialiseState();
		for(Player player : playerList)
		{
			player.setWorld(this.world);
		}
		
	}
	
	@Override
	public void initialiseState() {
		
		ListenerComponent listenerComponent = null;

		this.world = new World(renderMaster);// A new fantastic point of view/No one to tell us no or where to go/Or say we're only dreaming
		
		
		
		// Add Cameras
		Camera cam = ((DebugRenderMaster)renderMaster).addView(new Rectangle(0,300,800,300));//TODO RPETTY Why do you keep a reference to these cameras but still refer to them by their position in the list? omg
		Camera cam2 =((DebugRenderMaster)renderMaster).addView(new Rectangle(0,0,800,300));//
		cameras.add(cam);
		cameras.add(cam2);
		

		
		// Add and start music
		SoundEmitter musicComponent=this.soundMaster.getSoundComponent("assets/sound/ACiv Battle 2.wav", true); 
		musicComponent.playSound();
		
		// Set initial position?
		cameras.get(0).setPosition(new Vector3f(-50,40,-30));
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
		
		this.world.update(playerList);
		
		int i = 0;
		Vector3f camPos, targ; 
		for(Player player: playerList){
			//set the camera position
			camPos = player.getKart().graphicsComponent.getTransformedVector(0.0f, 35.0f, -50f, true);
			targ = player.getKart().graphicsComponent.getTransformedVector(0.0f, 1.0f, 0.0f, true);
			
			// Camera setting
			cameras.get(i).setPosition(camPos);
			cameras.get(i).setTarget(targ);
			i++;
		}
		
		renderMaster.draw();
	}
	
}
