package World;

import org.lwjgl.util.vector.Vector3f;

import Controller.GameController;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;

public class Kart {
	
	//some statics which help initialize the graphics
	private static float wheelFrontOffset = 5.0f;
	private static float wheelBackOffset = 3.125f;
	private static float wheelSideOffset = 4.125f;
	private static float wheelHeightOffset = 1.8f;
	private static float wheelRad = 1.8f;
	
	
	
	public GameController controller;
	
	public GraphicsComponent graphicsComponent;
	//refs for the wheels, rider, and hat
	private GraphicsComponent wheelFL,wheelFR, wheelBL, wheelBR;
	private GraphicsComponent rider;
	private GraphicsComponent hat;
	
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
		
		wheelFL = graphicsComponent.addSubComponent("wheel", renderer);
		wheelFR = graphicsComponent.addSubComponent("wheel", renderer);
		wheelBL = graphicsComponent.addSubComponent("wheel", renderer);
		wheelBR = graphicsComponent.addSubComponent("wheel", renderer);
		
		rider = graphicsComponent.addSubComponent("test", renderer);
		
		hat = rider.addSubComponent("hat", renderer);

		wheelFL.setPosition(new Vector3f(wheelSideOffset, wheelHeightOffset, wheelFrontOffset));
		wheelFR.setPosition(new Vector3f(-wheelSideOffset, wheelHeightOffset, wheelFrontOffset));

		wheelBL.setPosition(new Vector3f(wheelSideOffset, wheelHeightOffset, -wheelBackOffset));
		wheelBR.setPosition(new Vector3f(-wheelSideOffset, wheelHeightOffset, -wheelBackOffset));
		
		rider.setPosition(new Vector3f(0,4,0));
		hat.setPosition(new Vector3f(0,2,-0.35f));
		
		
		
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		
		this.turn = 0f;
		this.speed = 0f;
		this.sliding = false;
		
	}
	
	public void killmenow(float elec360power)
	{
		graphicsComponent.setPosition(new Vector3f(this.position.x + 10f * (float)Math.cos(elec360power/90f), 0,this.position.z +  10f * (float)Math.sin(elec360power/90f)));
		graphicsComponent.setRotation(new Vector3f(0,-3.1415f * 0.333f * (elec360power/90f),0));
		
	}
	
	public void update()
	{
		//get input
		
		
		
		
	}
	
	
}
