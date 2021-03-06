package States;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Controller.ControllerManager;
import Controller.ControllerType;
import Controller.EventManager;
import Controller.GameController;
import Graphics.Camera;
import Graphics.DebugRenderMaster;
import Graphics.RenderMaster;
import Graphics.RenderMasterFactory;
import Sound.SoundEmitter;
import Sound.SoundMaster;
import World.Kart;
import World.Persona;
import World.Player;

public class StateContext {
	
	private static final int DEFAULT_CONTROLLER_LOCK 	= -1;
	
	protected static GameState RACING_STATE; 
	protected static GameState PAUSE_MENU_STATE;
	
	private RenderMaster 		renderMaster;
	private SoundMaster 		soundMaster;
	private ControllerManager 	controllerManager;	
	private EventManager 		eventManager;
	private GameState 			gameState;
	private int 				lockedControllerId; // ID of Controller with "lock" (For Pausing and Such). 0 For Nobody
	private float				offset; //player creation offset for start
	
	
	
	private List<Player>		playerList;
	
	public StateContext() {
		renderMaster	 	= RenderMasterFactory.getRenderMaster();
		soundMaster 		= new SoundMaster();
		controllerManager 	= new ControllerManager();
		eventManager 		= new EventManager();
		playerList 			= new ArrayList<Player>();
		offset				= 0;
		
		loadModels();
		addPlayer(ControllerType.KEYBOARD);
		//addPlayer(ControllerType.XBOX);
		//addPlayer(ControllerType.XBOX);
		
		RACING_STATE 		= new RacingState(renderMaster, soundMaster, playerList);
		PAUSE_MENU_STATE 	= new PauseMenuState( soundMaster);

		setState(RACING_STATE);
		
		setLockedControllerId(DEFAULT_CONTROLLER_LOCK);
		

	
		
		/*
		SoundEmitter sound1 = this.soundMaster.getSoundComponent("assets/sound/carIdle.wav", false);
		SoundEmitter sound2 = this.soundMaster.getSoundComponent("assets/sound/carMaxSpeed.wav", false);
		sound1.setSoundGain(.5f);
		sound2.setSoundGain(.5f);
		sound1.playSound();
		sound2.playSound();*/
		
	}
	
	private void loadModels() {
		renderMaster.loadModel("test");
		renderMaster.loadModel("testTer");
		renderMaster.loadModel("kart");
		renderMaster.loadModel("hat");
		renderMaster.loadModel("wheel");
		renderMaster.loadModel("aktext");
		renderMaster.loadModel("rocket");
		renderMaster.loadModel("item");
		renderMaster.loadModel("nightFactory");
		renderMaster.loadModel("sam");
		renderMaster.loadModel("goop");
		renderMaster.loadModel("michael");
		renderMaster.loadModel("pizza");
		renderMaster.loadModel("mine");
	}
	
	private void addPlayer(ControllerType controllerType){
		// Stuff a Player Needs
		GameController gameController = controllerManager.addController(controllerType);
		
		Persona persona = new Persona(soundMaster, renderMaster);
		Kart kart = new Kart(renderMaster, persona);
		kart.killmeVec = new Vector3f(-300f + (10/4) * 150.0f, 0.0f, -300f + (10%4) * 150.0f);
		kart.killme = 12340f;
		Vector3f playerDelta = new Vector3f();
		Vector3f.add(kart.position, new Vector3f(8.5f*83.3f,0.0f, offset), kart.position);
		Camera cam = ((DebugRenderMaster)renderMaster).addView(new Rectangle(0,300 - (int)(offset*7.5f),800,300));
		
		Player player = new Player(renderMaster, gameController, kart, playerDelta, soundMaster.getListenerComponent(), cam);
		player.setSounds(this.soundMaster.getSoundComponent("assets/sound/carIdle.wav", false), 
				this.soundMaster.getSoundComponent("assets/sound/Car Accelerating.wav", false),
				this.soundMaster.getSoundComponent("assets/sound/carMaxSpeed.wav", false),
				this.soundMaster.getSoundComponent("assets/sound/car-brake.wav", false));
		playerList.add(player);
		
		offset += 40f;
	}
	
	public List<Player> getPlayerList()
	{
		return this.playerList;
	}
	
	public GameState getState(){
		return gameState;
	}
	
	public void setState(final GameState newState){
		gameState = newState;
	}
	
	public int getLockedControllerId(){
		return lockedControllerId;
	}
	
	public void setLockedControllerId(int newLockedControllerId){
		lockedControllerId = newLockedControllerId;
	}
	
	public void resetControllerLock() {
		setLockedControllerId(DEFAULT_CONTROLLER_LOCK);
	}

	public void useActionButton(StateContext stateContext, int invokingId) {
		gameState.useActionButton(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void useBackButton(StateContext stateContext, int invokingId) {
		gameState.useBackButton(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void useWeapon(StateContext stateContext, int invokingId) {
		gameState.useWeapon(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void pause(StateContext stateContext, int invokingId) {
		gameState.pause(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveUp(StateContext stateContext, int invokingId) {
		gameState.moveUp(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveDown(StateContext stateContext, int invokingId) {
		gameState.moveDown(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveLeft(StateContext stateContext, int invokingId) {
		gameState.moveLeft(stateContext, renderMaster, soundMaster, invokingId);
	}

	public void moveRight(StateContext stateContext, int invokingId) {
		gameState.moveRight(stateContext, renderMaster, soundMaster, invokingId);
	}
	
	public boolean execute(){
		controllerManager.poll();
		eventManager.handleEvents(controllerManager.getEvents(), this, renderMaster);
		
		gameState.execute(playerList);
		
		if(Display.isCloseRequested()){
			soundMaster.cleanUpALData();
			return false;
		}
		return true;
	}
}
