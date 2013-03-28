package World;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;
import Sound.SoundEmitter;

public class Rocket implements Entity {
	public Player owner;
	public World world;
	
	protected static List<Rocket> deadRockets = new ArrayList<Rocket>();
	
	public Vector3f position;
	public Vector3f rotation;
	
	protected GraphicsComponent graphicsComponent;
	protected Light light;
	
	
	private float alternator;
	private boolean inc;
	
	public CollisionBox collisionBox;
	public SoundEmitter missleLaunch;
	public Rocket(Vector3f position, Vector3f rotation, RenderMaster renderMaster, World world, Player player, SoundEmitter missleLaunch)
	{
		this.world = world;
		this.owner = player;
		
		this.position = position;
		this.rotation = rotation;
		
		this.missleLaunch = missleLaunch;
		missleLaunch.setSoundPosition(position);
		this.graphicsComponent = renderMaster.addModel("rocket");
		this.light = renderMaster.addLight();
		
		this.alternator = 0.0f;
		this.inc = true;
		light.setColor(new Vector3f(1,alternator,0));
		light.setRad(100);
		
		this.collisionBox = new CollisionBox(this.position, new Vector3f(12,12,12));
		
		missleLaunch.playSound();
		
	}
	
	public Player getOwner(){
		return owner;
	}
	
	public GraphicsComponent getGraphicsComponent(){
		return graphicsComponent;
	}
	
	public Light getLight(){
		return light;
	}
	
	public void destroySoundEmitters(){
		missleLaunch.removeSound();
	}
	
	@Override
	public void update()
	{
		Vector3f delta = this.graphicsComponent.getTransformedVector(0, 0, 5, false);
		
		Vector3f.add(delta, this.position, this.position);

		this.graphicsComponent.setPosition(position);

		this.graphicsComponent.setRotation(rotation);
		
		this.missleLaunch.setSoundPosition(this.position);

		//cute flashy light
		if(inc)
		{
			this.alternator += 0.1f;
		}
		else
		{
			this.alternator -= 0.1f;
		}
		if(alternator >1.0f || alternator < 0.0f)
		{
			inc = !inc;
		}
		
		this.light.setPosition(new Vector3f(position.x, position.y + 20, position.z));
		light.setColor(new Vector3f(1,alternator,0));	
		
		//collision?
/*		Kart toRemove = null;
		
		for(Kart k : world.donutKarts)
		{
			if (this.collisionBox.bIntersects(k.collisionBox))
			{
				toRemove = k;
				break;
			}
		}
		if(toRemove != null)
		{
			toRemove.graphicsComponent.setPosition(new Vector3f(0,2000,0));
			world.donutKarts.remove(toRemove);
		}
	*/
		
		//remove if wall is hit
		for(CollisionBox other : world.walls)
		{
			if(this.collisionBox.bIntersects(other))
			{
				Rocket.deadRockets.add(this);
				break;
			}
		}
		
		//remove if player is hit
		for(Player player : world.players){
			if(this.collisionBox.bIntersects(player.getKart().collisionBox) && !this.getOwner().equals(player)){
				player.hitPlayer();
				Rocket.deadRockets.add(this);				
			}
		}
		
		
	}
	
	
}

