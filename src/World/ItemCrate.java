package World;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Graphics.GraphicsComponent;
import Graphics.RenderMaster;

public class ItemCrate {
	
	public RenderMaster renderMaster;
	public World		world;

	public GraphicsComponent itemCrate;
	
	public CollisionBox collisionBox;
	
	private Vector3f position;
	private Vector3f rotation;
	
	private int	resetCounter;
	
	public ItemCrate(RenderMaster renderMaster, World world, Vector3f initialPosition){
		this.renderMaster 	= renderMaster;
		this.world 			= world;
		
		this.itemCrate		= renderMaster.addModel("item"); // TODO Replace with an actual item crate
		this.collisionBox	= new CollisionBox(new Vector3f(), new Vector3f(8,8,8));
		this.position 		= initialPosition;
		this.rotation 		= new Vector3f();
		this.resetCounter	= 0;
	}
	
	public void update() {
		if(resetCounter > 1) resetCounter--;
		else if(resetCounter == 1){
			position.y += 50f;
			resetCounter--;
			itemCrate.setPosition(position);
		} else {
			itemCrate.setPosition(this.position);
			itemCrate.setRotation(this.rotation);
		
			collisionBox.setPosition(this.position);
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
	}
	
	public EntityType generateItem(){
		return EntityType.ROCKET;
	}
}
