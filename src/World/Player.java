package World;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Controller.GameController;
import Graphics.DebugGraphicsComponent;

public class Player {
	
	private static final float DEFAULT_ACCEL 	= 1.0f;
	private static final float MAX_ACCEL		= 2.0f;
	private static final float ACCEL_SCALE_UP	= 1.08f;
	private static final float ACCEL_SCALE_DOWN	= 0.97f;
	
	private GameController 	gameController;
	private Kart			kart;
	
	private Vector4f		playerDelta;
	
	private float 			jump;
	private float			speed;
	private float			acceleration;
	private int				direction; // 1 for forward, -1 for back, 0 for none
	
	public Player(GameController gameController, Kart kart, Vector4f playerDelta){
		this.gameController = gameController;
		this.kart 			= kart;
		this.playerDelta	= playerDelta;
		
		acceleration 		= DEFAULT_ACCEL;
		direction 			= 0;
		speed 				= 0f;
		jump 				= 0f;
	}
	
	public GameController getGameController(){
		return gameController;
	}
	
	public Kart getKart(){
		return kart;
	}
	
	public Vector4f getPlayerDelta(){
		return playerDelta;
	}
	
	public void setPlayerDelta(Vector4f playerDelta){
		this.playerDelta = playerDelta;
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
	
	public void update(){
		Vector4f.add(playerDelta, new Vector4f(0, getJump(), getAcceleration(), 0), playerDelta); // Forward/Backward movement
		Vector3f.add(getKart().getRotation(), new Vector3f(0, getGameController().getLeftRightValue()/-80f, 0), getKart().getRotation()); //Left/Right Movement
		
		Matrix4f.transform(((DebugGraphicsComponent)getKart().graphicsComponent).getInvModelMat(), playerDelta, playerDelta);	
		Vector3f.add(getKart().getPosition(), new Vector3f(playerDelta), getKart().getPosition());
		getKart().update();
		
		playerDelta.set(0, 0, 0, 0);
	}

}
