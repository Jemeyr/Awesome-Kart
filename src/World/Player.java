package World;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Controller.GameController;
import Graphics.DebugGraphicsComponent;

public class Player {
	
	private GameController 	gameController;
	private Kart			kart;
	
	private Vector4f		playerDelta;
	
	public Player(GameController gameController, Kart kart, Vector4f playerDelta){
		this.gameController = gameController;
		this.kart 			= kart;
		this.playerDelta	= playerDelta;
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
	
	public void update(){
		Vector4f.add(playerDelta, new Vector4f(0, 0, getGameController().getUpDownValue(), 0), playerDelta);
		Vector3f.add(getKart().getRotation(), new Vector3f(0, getGameController().getLeftRightValue()/-80f, 0), getKart().getRotation());
		
		Matrix4f.transform(((DebugGraphicsComponent)getKart().graphicsComponent).getInvModelMat(), playerDelta, playerDelta);	
		Vector3f.add(getKart().getPosition(), new Vector3f(playerDelta), getKart().getPosition());
		getKart().update();
		
		playerDelta.set(0, 0, 0, 0);
	}

}
