package World;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;

public class Mine implements Entity {

	private GraphicsComponent 	graphicsComponent;
	private Light 				light;
	
	protected static List<Mine> deadMines = new ArrayList<Mine>();
	
	public RenderMaster renderMaster;
	public World world;
	public Player owner;
	
	public Vector3f position;
	public Vector3f rotation;
	
	public CollisionBox collisionBox;
	
	private float alternator;
	private boolean inc;
	private boolean switched;
	
	public Mine(Vector3f position, Vector3f rotation, RenderMaster renderMaster, World world, Player player){
		this.renderMaster = renderMaster;
		this.world = world;
		this.owner = player;
		this.position = position;
		this.rotation = rotation;
		
		this.graphicsComponent = renderMaster.addModel("mine");
		this.light = renderMaster.addLight();
		
		this.alternator = 0.0f;
		this.inc = true;
		this.switched = false;
		this.light.setPosition(new Vector3f(position.x, position.y + 20, position.z));
		this.light.setColor(new Vector3f(1,-1,-1));
		this.light.setRad(40);
		
		this.collisionBox	= new CollisionBox(this.position, new Vector3f(12,12,12));
	}
	
	@Override
	public GraphicsComponent getGraphicsComponent() {
		return graphicsComponent;
	}

	@Override
	public Light getLight() {
		return light;
	}

	@Override
	public void update() {
		this.graphicsComponent.setPosition(position);
		this.graphicsComponent.setRotation(rotation);
		
		if(inc) {
			this.alternator += 0.1f;
		}
		else {
			this.alternator -= 0.02f;
		}
		
		if((inc && alternator > 1.0f) || alternator < 0.0f) {
			inc = !inc;
			switched = true;
		}
		
		if(switched){
			if(inc){
				this.light.setPosition(new Vector3f(position.x, position.y + 20, position.z));
			} else {
				this.light.setPosition(new Vector3f(position.x, position.y - 1000, position.z));
			}
			switched = false;
		}
		
		for(CollisionBox other : world.walls)
		{
			if(this.collisionBox.bIntersects(other))
			{
				Mine.deadMines.add(this);
				break;
			}
		}
		
		//remove if player is hit
		for(Player player : world.players){
			if(this.collisionBox.bIntersects(player.getKart().collisionBox)){
				player.hitPlayer();
				Mine.deadMines.add(this);				
			}
		}
	}

}
