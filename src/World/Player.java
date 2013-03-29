package World;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Controller.GameController;
import Graphics.Camera;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;
import Sound.ListenerComponent;
import Sound.SoundEmitter;
import States.GameState;


public class Player {
	
	private static final float DEFAULT_ACCEL 	= 1.0f;
	private static final float MAX_ACCEL		= 2.0f;
	private static final float ACCEL_SCALE_UP	= 1.08f;
	private static final float ACCEL_SCALE_DOWN	= 0.97f;
	private static Random gen = new Random();
	
	private RenderMaster		renderMaster;
	private GameController 		gameController;
	private Kart				kart;
	private World				world;
	private ListenerComponent 	listenerComponent; 
	private Vector3f			playerDelta;
	private Camera				camera;
	private Persona				persona;
	private GraphicsComponent	itemGraphic;
	
	private EntityType			heldItemType;
	private GameState 			racingState;
	private int					powerLevel;
	private int					ammo;
	
	private float 				jump;
	private boolean			onGround;
	private float				speed;
	private float				acceleration;
	private int					direction; // 1 for forward, -1 for back, 0 for none
	
	private boolean				isHit;
	private boolean				inPit;
	private float					spin;
	
	protected Checkpoint currCheckPoint = null;
	protected Checkpoint nextCheckPoint = null;
	
	public int lapsCompleted = 0;
	public boolean finishedRace = false;
	public int playerID= 0;
	
	protected SoundEmitter 		carIdle;
	protected SoundEmitter 		carAcc;
	protected SoundEmitter 		carMaxSpeed;
	protected SoundEmitter 		carBrake;
	
	public Player(RenderMaster renderMaster, GameController gameController,Kart kart, Vector3f playerDelta, ListenerComponent listenerComponent, Camera camera){
		this.renderMaster		= renderMaster;
		this.gameController 	= gameController;
		this.kart 				= kart;
		this.playerDelta		= playerDelta;
		this.listenerComponent 	= listenerComponent;
		this.camera				= camera;
		this.heldItemType 		= null; // Start with no item
		
		playerID 				= this.gameController.getId()+1;
		acceleration 			= DEFAULT_ACCEL;
		direction 				= 0;
		speed 					= 0f;
		jump 					= 0f;
		onGround				= true;
		inPit 					= false;
		powerLevel				= 0;
		ammo					= 0;
		
		isHit					= false;
		spin					= 0;
	}
	
	public void setRacingState(GameState racingState){
		this.racingState = racingState;
	}
	
	public void setSounds(SoundEmitter carIdle, SoundEmitter carAcc,SoundEmitter carMaxSpeed, SoundEmitter carBrake)
	{
		this.carIdle = carIdle;
		this.carAcc = carAcc;
		this.carMaxSpeed= carMaxSpeed;
		this.carBrake = carBrake;
		
		this.carIdle.setSoundGain(0.5f);
		this.carAcc.setSoundGain(0.2f);
		this.carMaxSpeed.setSoundGain(0.125f);
		this.carBrake.setSoundGain(1f);
		
	}
	
	
	public void setWorld(World w)
	{
		this.world = w;
	}
	
	public GameController getGameController(){
		return gameController;
	}
	
	public Kart getKart(){
		return kart;
	}
	
	public Vector3f getPlayerDelta(){
		return playerDelta;
	}
	
	public void setPlayerDelta(Vector3f playerDelta){
		this.playerDelta = playerDelta;
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public void setHeldItem(EntityType heldItemType){
		this.heldItemType = heldItemType; 
	}
	
	public void clearItem(){
		this.heldItemType 	= null;
		this.ammo			= 0;
		this.powerLevel		= 0;
		
		getKart().graphicsComponent.removeSubComponent(itemGraphic);
		renderMaster.removeModel(itemGraphic);
		
		itemGraphic = null;
	}
	
	/**
	 * Scales the speed so that you can speed up or slow down, or drift to
	 * a stop if you let go of the gas/reverse
	 * 
	 * @return
	 */
	private float getAcceleration(){
		float forwardBackValue = getGameController().getForwardBackValue();
		if(forwardBackValue != 0 && acceleration <= MAX_ACCEL){
			
			if(forwardBackValue >= direction){
				acceleration *= ACCEL_SCALE_UP;

				
			} else {
				acceleration = DEFAULT_ACCEL;
				direction = (forwardBackValue > 0) ? 1 : -1;
			
			}
			 
		}
		
		
		//play brake sound?
		if(forwardBackValue != 0){
			speed = forwardBackValue * acceleration;
			
			
			if(speed<MAX_ACCEL){
				this.carAcc.playSound();
				this.carIdle.stopSound();
				this.carMaxSpeed.stopSound();
				this.carBrake.stopSound();
			}
			else if (speed >= MAX_ACCEL){
				this.carMaxSpeed.playSound();
				this.carIdle.stopSound();
				this.carAcc.stopSound();
				this.carBrake.stopSound();
			}
		
		} 
		//play idle sound
		else {
			
			this.carIdle.playSound();
			
			this.carAcc.stopSound();
			this.carMaxSpeed.stopSound();
			this.carBrake.stopSound();
			speed *= ACCEL_SCALE_DOWN;
		}
		
		return speed;
	}
	
	private float getJump(){
		float jumpValue = getGameController().getJumpValue();

		inPit = false;
		for(CollisionBox other : world.pits)
		{
			if(this.getKart().collisionBox.bIntersects(other))
			{
				inPit = true;
				onGround = false;
				break;
			}
		}
	
		
		//if you're in the air
		if(!onGround)
		{
			//if you're above the respective min height
			if((!inPit && this.getKart().position.y + jump <= 0.0f  &&this.getKart().position.y + jump >= -10.0f) || (inPit && this.getKart().position.y + jump <= -240.0f))
			{
				//set to min height and stop falling
				jump = 0;
				if(inPit)
				{
					this.getKart().position.y = -240f;	
				}
				else if (this.getKart().position.y > -5f) 
				{
					this.getKart().position.y = 0f;
				}
				onGround = true;
			}
			else
			{
				jump -= 0.2f;
			}
		}
		
		//reset position
		/*if(!inPit && onGround)
		{
			this.getKart().position.y = 0f;
		}
		*/
		
		if(jumpValue == 1 && onGround)
		{
			jump = 2.5f;
			onGround = false;
			//initial velocity up
		}
		
		
		return jump;
	}
	
	public void useWeapon()
	{
		
		if(heldItemType != null){
			
			if(gen.nextInt(5) == 0)
			{
				getKart().getPersona().getShootPerson().playSound();
			}
			float forBack = this.getGameController().getUpDownValue();
			Vector3f firePosition = this.kart.graphicsComponent.getTransformedVector(0,0,(forBack > 0.4 ? -5f : 5f), true);
			switch(heldItemType){
				case ROCKET: {
					world.addRocket(firePosition, new Vector3f(0,this.kart.getRotation().y + (forBack > 0.4 ? -3.14f : 0f),0), this);
					if(--ammo == 0) clearItem();
					break;
				}
				case MINE: {
					Vector3f dropPosition = this.kart.graphicsComponent.getTransformedVector(0,0,-15, true);
					world.addMine(dropPosition, new Vector3f(0, this.kart.getRotation().y,0), this);
					if(--ammo == 0) clearItem();
					break;
				}
			}
		}
	}
	
	public void hitPlayer(){
		isHit 	= true;
		spin	= 6.28f;
		getKart().getPersona().getHit().playSound();
	}
	
	public void updateItem(EntityType itemType){
		if(heldItemType == null){
			setHeldItem(itemType);
			
			switch(itemType)
			{
				case ROCKET:
				{
					itemGraphic = renderMaster.addSubModel("rocket");
					getKart().graphicsComponent.addAsSubComponent(itemGraphic);
					itemGraphic.setPosition(new Vector3f(0, 15f, 0));
					itemGraphic.setRotation(new Vector3f(0, 1.57f, 0));
					
					break;
				}
				case MINE:
				{
					itemGraphic = renderMaster.addSubModel("mine");
					getKart().graphicsComponent.addAsSubComponent(itemGraphic);
					itemGraphic.setPosition(new Vector3f(0, 15f, 0));
					itemGraphic.setRotation(new Vector3f(1.57f, 0, 0));
					break;
				}
				default:
					break;
			}

			
			powerLevel = 1;
			ammo = 1;
		} else {
			powerLevel = 2;
			if(powerLevel == 2) ammo = 3;
		}
	}
	
	public void update(){
		if(!isHit){
			Vector3f.add(playerDelta, new Vector3f(0, getJump(), getAcceleration()), playerDelta); // Forward/Backward movement
			Vector3f.add(getKart().getRotation(), new Vector3f(0, getGameController().getLeftRightValue()/-20f, 0), getKart().getRotation()); //Left/Right Movement
			
			playerDelta = getKart().graphicsComponent.getTransformedVector(playerDelta, false);
			Vector3f.add(getKart().getPosition(), playerDelta, getKart().getPosition());
			
			
			Vector3f collide = new Vector3f();
			for(CollisionBox other : world.walls)
			{
				collide = getKart().collisionBox.intersects(other); 
				if(collide != null)
				{
					
					Vector3f.add(getKart().getPosition(), collide, getKart().getPosition());
					this.acceleration *= 0.85;
					if(this.acceleration < 0.1)
					{
						getKart().getPersona().getHit().playSound();
						this.acceleration = 0.1f;
					}
					//break;
				}
			}
			getKart().update();
			
			//check if you fell
			if(getKart().position.y <= -200.0f)
			{
				getKart().position = new Vector3f(currCheckPoint.post);
				getKart().getRotation().y = currCheckPoint.rotation;
				getKart().update();
				
				this.onGround = true;
				this.inPit = false;
				this.jump = 0;
				this.ammo = ammo > 1 ? 1 : ammo;
				getKart().getPersona().getHit().playSound();
			}
			
			//Check CheckPoints
			if(currCheckPoint!=null)
			{
				if(world.reachedCheckpoint(nextCheckPoint, getKart().getPosition()))
				{
					
					if (nextCheckPoint.isFinishLine) {
					
						lapsCompleted++;
						System.out.println("Player "+playerID+" has completed a lap " + nextCheckPoint.pointNum);
					

						racingState.reportLapCompleted(this);
						
						
					}

					currCheckPoint = nextCheckPoint;
					nextCheckPoint = world.getNextChekpoint(currCheckPoint);
					
				}
			}
			
			
			//This will cause a null exception if used with ryan's ControllerMain test class
			listenerComponent.setListenerPosition(getKart().getPosition());
			playerDelta.set(0, 0, 0);
		} else {
			Vector3f.add(getKart().getRotation(), new Vector3f(0, 0.2f, 0), getKart().getRotation()); 
			playerDelta = getKart().graphicsComponent.getTransformedVector(playerDelta, false);
			Vector3f.add(getKart().getPosition(), playerDelta, getKart().getPosition());
			getKart().update();
			spin -= 0.2f;
			if(spin <= 0.0f) 
				{
					isHit = false;
					spin = 0.0f;
				}
		}
	}
	
	public void updateCamera(){
		Vector3f camPos, targ; 
		camPos = getKart().graphicsComponent.getTransformedVector(0.0f, 25.0f, -50f, true);
		targ = getKart().graphicsComponent.getTransformedVector(0.0f, 1.0f, 0.0f, true);
		
		getCamera().setPosition(camPos);
		getCamera().setTarget(targ);
	}
	
	@Override
	public boolean equals(Object other){
		if(!(other instanceof Player)) return false;
		
		Player player = (Player)other;
		return (this.playerID == player.playerID);
	}

}
