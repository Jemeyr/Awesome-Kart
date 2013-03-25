package States;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Controller.ControllerType;
import Controller.GameController;
import Graphics.Camera;
import Graphics.DebugGraphicsComponent;
import Graphics.DebugRenderMaster;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;
import Sound.SoundEmitter;
import Sound.SoundMaster;
import World.Kart;
import World.Player;


/**
 * This is the state for when the player is actually racing.
 * These functions will delegate elsewhere to do the actual work.
 *
 */
public class RacingState implements GameState {
	
	public static final boolean DEBUG = false;
	
	private RenderMaster 								renderMaster;
	private SoundMaster									soundMaster;
	private List<Kart>									donutKarts;
	private List<Camera>								cameras;
	private HashMap<String, DebugGraphicsComponent> 	otherThings;
	private HashMap<String, GraphicsComponent>			otherGraphics;
	private int											elec360power;
		
	public RacingState(RenderMaster renderMaster, SoundMaster soundMaster){
		this.renderMaster 	= renderMaster;
		this.soundMaster	= soundMaster;
		donutKarts 			= new ArrayList<Kart>();
		cameras 			= new ArrayList<Camera>();
		otherThings			= new HashMap<String, DebugGraphicsComponent>();
		otherGraphics		= new HashMap<String, GraphicsComponent>();
		
		elec360power		= 0;
		
		initialiseState();
	}
	
	@Override
	public void initialiseState() {
		// Add Terrain
		addTerrain();
		
		// Add Donut Karts
		for(int i = 0; i < 16; i++)
		{
			if(i == 10) i++;
			Kart k = new Kart(renderMaster);
			k.killmeVec = new Vector3f(-300f + (i/4) * 150.0f, -22.5f, -300f + (i%4) * 150.0f);
			donutKarts.add(k);
			k.killme = i*1234f;
		}
		
		// Add Cameras
		Camera cam = ((DebugRenderMaster)renderMaster).addView(new Rectangle(0,300,800,300));
		Camera cam2 =((DebugRenderMaster)renderMaster).addView(new Rectangle(0,0,800,300));
		cameras.add(cam);
		cameras.add(cam2);
		
		// Add Triforce
		DebugGraphicsComponent triforce = (DebugGraphicsComponent)renderMaster.addModel("test");
		triforce.setPosition(new Vector3f(0,0.4f,0));
		otherThings.put("Triforce", triforce);
		
		// Add Awesome Kart Text
		GraphicsComponent text = renderMaster.addModel("aktext");
		text.setPosition(new Vector3f(-200, 40, 100));
		otherGraphics.put("AKText", text);
		
		// Add and start music
		SoundEmitter musicComponent=this.soundMaster.getSoundComponent("assets/sound/ACiv Battle 2.wav", true); 
		musicComponent.playSound();
		
		// Set initial position?
		cameras.get(0).setPosition(new Vector3f(-50,40,-30));
	}
	
	private void addTerrain(){
		renderMaster.addModel("testTer");
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
		
		elec360power++;
		
		// DO the donut karts
		for(Kart k : donutKarts)
		{ 
			k.killmenow(elec360power);
		}
		
		//triforce that rotates and flies away
		otherThings.get("Triforce").setRotation(new Vector3f(3.14f * (elec360power/1500f),-3.14f * (elec360power/1500f), 3.14f * (elec360power/1500f)));
		otherThings.get("Triforce").setPosition(new Vector3f(-30f + 60*elec360power/450f, 0, 0));
		
		// Rotating Text
		otherGraphics.get("AKText").setRotation(new Vector3f(0,elec360power/1500f, 0));
		
		//set the camera position
		Vector4f campos = new Vector4f(0,35,-50, 1);	//12.5 is the lateral offset of the kart, 8 height, 50 behind
		Vector4f targ = new Vector4f(0,1,0,1);		//divide by 2, I don't know why, height one for overhead.
		Vector4f campos2 = new Vector4f(0,35, 50,1);
		
		// Some matrix shit, hardcoded to use player 1
		Matrix4f modelInv = ((DebugGraphicsComponent)playerList.get(0).getKart().graphicsComponent).getInvModelMat();
		Matrix4f.transform(modelInv, campos, campos);
		modelInv = ((DebugGraphicsComponent)playerList.get(0).getKart().graphicsComponent).getInvModelMat();
		Matrix4f.transform(modelInv, targ, targ);
		
		modelInv = ((DebugGraphicsComponent)playerList.get(0).getKart().graphicsComponent).getInvModelMat();
		Matrix4f.transform(modelInv, campos2, campos2);
		
		// Camera setting
		cameras.get(0).setPosition(new Vector3f(campos.x, campos.y, campos.z));
		cameras.get(0).setTarget(new Vector3f(targ.x, targ.y, targ.z));
		
		cameras.get(1).setPosition(new Vector3f(campos2.x, campos2.y, campos2.z));
		cameras.get(1).setTarget(new Vector3f(targ.x, targ.y, targ.z));
		
		renderMaster.draw();
	}
	
}
