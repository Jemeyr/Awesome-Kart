package World;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;

public class Kart {
	
	//some statics which help initialize the graphics
	private static float wheelFrontOffset = 5.0f;
	private static float wheelBackOffset = 3.125f;
	private static float wheelSideOffset = 4.125f;
	private static float wheelHeightOffset = 1.8f;
	//private static float wheelRad = 1.8f;
	
	public float killme;
	public Vector3f killmeVec;
	
	public GraphicsComponent graphicsComponent;
	//refs for the wheels, rider, and hat
	private GraphicsComponent wheelFL,wheelFR, wheelBL, wheelBR;
	private GraphicsComponent rider;
	private GraphicsComponent hat;
	

	public CollisionBox collisionBox;
	
	public Vector3f position;
	private Vector3f rotation;
	
	public float turn;
	public float speed;
	public boolean sliding;
	
	private Persona persona;
	
	
	//public heldweapon
	
	public Kart(RenderMaster renderer, Persona persona)
	{	
		this.graphicsComponent 	= renderer.addModel("kart");
		this.persona			= persona;
		
		wheelFL = graphicsComponent.addSubComponent("wheel", renderer);
		wheelFR = graphicsComponent.addSubComponent("wheel", renderer);
		wheelBL = graphicsComponent.addSubComponent("wheel", renderer);
		wheelBR = graphicsComponent.addSubComponent("wheel", renderer);
		
		if(persona != null)
		{
			rider = graphicsComponent.addAsSubComponent(persona.getModel());
		}
		else
		{
			rider = graphicsComponent.addSubComponent("kart", renderer);
		}
		hat = rider.addSubComponent("hat", renderer);

		wheelFL.setPosition(new Vector3f(wheelSideOffset, wheelHeightOffset, wheelFrontOffset));
		wheelFR.setPosition(new Vector3f(-wheelSideOffset, wheelHeightOffset, wheelFrontOffset));

		wheelBL.setPosition(new Vector3f(wheelSideOffset, wheelHeightOffset, -wheelBackOffset));
		wheelBR.setPosition(new Vector3f(-wheelSideOffset, wheelHeightOffset, -wheelBackOffset));
		
		rider.setPosition(new Vector3f(0,4,0));
		hat.setPosition(new Vector3f(0,2,-0.35f));
		
		this.collisionBox = new CollisionBox(new Vector3f(), new Vector3f(4,4,4));
		
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		
		this.turn = 0f;
		this.speed = 0f;
		this.sliding = false;
		
	}
	
	public void killmenow(float e)
	{
		float elec360power = e/90f + killme;
		Vector3f pos = new Vector3f(killmeVec.x + 10f * (float)Math.cos(elec360power), 0.0f,killmeVec.z +  10f * (float)Math.sin(elec360power));
		Vector3f rot = new Vector3f(0,-(elec360power),0);
		
		this.position = pos;
		
		this.rotation = rot;
		update();
	}
	
	public void update()
	{
		//get input
		graphicsComponent.setPosition(this.position);
		graphicsComponent.setRotation(this.rotation);
		
		collisionBox.setPosition(this.position);
	}
	
	public Persona getPersona()
	{
		return this.persona;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	
}
