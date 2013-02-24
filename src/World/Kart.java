package World;

import org.lwjgl.util.vector.Vector3f;

import Controller.GameController;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;

public class Kart {

	public GameController controller;
	
	public GraphicsComponent graphicsComponent;
	public Vector3f position;
	public Vector3f rotation;
	
	public float turn;
	public float speed;
	public boolean sliding;
	
	//public heldweapon
	
	public Kart(GameController controller, RenderMaster renderer)
	{
		this.controller = controller;
		
		this.graphicsComponent = renderer.addModel("kart");
		
		
		
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		
		this.turn = 0f;
		this.speed = 0f;
		this.sliding = false;
		
	}
	
	public void update()
	{
		//get input
		
		
		
		
	}
	
	
}
