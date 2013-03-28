package World;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;

public class ItemCrate {
	
	public RenderMaster renderMaster;
	public World		world;

	public GraphicsComponent itemCrate;
	public CollisionBox collisionBox;
	
	private Vector3f position;
	private Vector3f rotation;
	
	private Light light;
	private float alternator;
	private boolean inc;
	
	private int	resetCounter;
	
	public ItemCrate(RenderMaster renderMaster, World world, Vector3f initialPosition){
		this.renderMaster 	= renderMaster;
		this.world 			= world;
		
		this.itemCrate		= renderMaster.addModel("item"); // TODO Replace with an actual item crate
		this.collisionBox	= new CollisionBox(new Vector3f(), new Vector3f(12,12,12));
		this.position 		= initialPosition;
		this.rotation 		= new Vector3f();
		this.resetCounter	= 0;
		
		this.light 			= renderMaster.addLight();
		this.alternator 	= 0.0f;
		this.inc 			= true;
		
		light.setColor(new Vector3f(1,alternator,0));
		light.setRad(50);
	}
	
	public void update() {
		if(resetCounter > 1) resetCounter--;
		else if(resetCounter == 1){
			position.y += 50f;
			resetCounter--;
			itemCrate.setPosition(position);
			light.setPosition(position);
		} else {
			itemCrate.setPosition(this.position);
			itemCrate.setRotation(this.rotation);
		
			collisionBox.setPosition(this.position);
			
			if(inc){
				this.alternator += 0.1f;
			}
			else {
				this.alternator -= 0.1f;
			}
			
			if(alternator >1.0f || alternator < 0.0f) {
				inc = !inc;
			}
			
			this.light.setPosition(new Vector3f(position.x, position.y + 20, position.z));
			light.setColor(new Vector3f(1,alternator,0));	
		}
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(Vector3f rotation){
		this.rotation = rotation;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void disappear(){
		resetCounter = 300;
		position.y -= 50f;
		itemCrate.setPosition(position);
		light.setPosition(position);
	}
	
	public EntityType generateItem(){
		return EntityType.ROCKET;
	}
}
