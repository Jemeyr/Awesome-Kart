package World;

import org.lwjgl.util.vector.Vector3f;

import Controller.GameController;
import Graphics.Camera;
import Sound.ListenerComponent;


public class Player {
	
	private static final float DEFAULT_ACCEL 	= 1.0f;
	private static final float MAX_ACCEL		= 2.0f;
	private static final float ACCEL_SCALE_UP	= 1.08f;
	private static final float ACCEL_SCALE_DOWN	= 0.97f;
	
	private GameController 		gameController;
	private Kart				kart;
	private World				world;
	private ListenerComponent 	listenerComponent; 
	private Vector3f			playerDelta;
	private Camera				camera;
	
	private float 				jump;
	private float				speed;
	private float				acceleration;
	private int					direction; // 1 for forward, -1 for back, 0 for none
	
	
	
	protected Checkpoint currCheckPoint = null;
	protected Checkpoint nextCheckPoint = null;
	
	public int lapsCompleted = 0;
	public boolean finishedRace = false;
	public int playerID= 0;
	
	public Player(GameController gameController, Kart kart, Vector3f playerDelta, ListenerComponent listenerComponent, Camera camera){
		this.gameController 	= gameController;
		this.kart 				= kart;
		this.playerDelta		= playerDelta;
		this.listenerComponent 	= listenerComponent;
		this.camera				= camera;
		
		playerID = this.gameController.getBackValue()+1;
		acceleration 			= DEFAULT_ACCEL;
		direction 				= 0;
		speed 					= 0f;
		jump 					= 0f;
	}
	
	public void setWorld(World w)
	{
		this.world =w;
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
		
		if(forwardBackValue != 0){
			speed = forwardBackValue * acceleration;
		} else {
			speed *= ACCEL_SCALE_DOWN;
		}
		
		return speed;
	}
	
	private float getJump(){
		float jumpValue = getGameController().getJumpValue();
		if(jump > 0f || jumpValue == 1){
			if (jump < 20f) {
				return (jump++ < 10f) ? 1f : -1f;
			} else {
				jump = 0f; 
				return jump;
			} 
		}
		
		return 0f;
	}
	
	public void useWeapon()
	{
		Vector3f firePosition = this.kart.graphicsComponent.getTransformedVector(0,0,5, true);
		
		world.addRocket(firePosition, new Vector3f(0,this.kart.getRotation().y,0));
	}
	
	public void update(){
		Vector3f.add(playerDelta, new Vector3f(0, getJump(), getAcceleration()), playerDelta); // Forward/Backward movement
		Vector3f.add(getKart().getRotation(), new Vector3f(0, getGameController().getLeftRightValue()/-20f, 0), getKart().getRotation()); //Left/Right Movement
		
		playerDelta = getKart().graphicsComponent.getTransformedVector(playerDelta, false);
		Vector3f.add(getKart().getPosition(), playerDelta, getKart().getPosition());
		getKart().update();
		
		//Check CheckPoints
		if(currCheckPoint!=null)
		{
			if(world.reachedCheckpoint(nextCheckPoint, getKart().getPosition()))
			{
				if (nextCheckPoint.isFinishLine)
				{
					lapsCompleted++;
					System.out.println("Player "+playerID+" has completed a lap");
				}
				
				currCheckPoint = nextCheckPoint;
				nextCheckPoint = world.getNextChekpoint(currCheckPoint);
				
			}
		}
		
		
		//This will cause a null exception if used with ryan's ControllerMain test class
		listenerComponent.setListenerPosition(getKart().getPosition());
		playerDelta.set(0, 0, 0);
	}
	
	public void updateCamera(){
		Vector3f camPos, targ; 
		camPos = getKart().graphicsComponent.getTransformedVector(0.0f, 35.0f, -50f, true);
		targ = getKart().graphicsComponent.getTransformedVector(0.0f, 1.0f, 0.0f, true);
		
		getCamera().setPosition(camPos);
		getCamera().setTarget(targ);
	}

}
