package World;

import org.lwjgl.util.vector.Vector3f;

import Collision.CollisionBox;
import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;

public class Rocket implements Entity {
	public Player owner;
	public World world;
	
	public Vector3f position;
	public Vector3f rotation;
	
	private GraphicsComponent graphicsComponent;
	private Light light;
	
	private float alternator;
	private boolean inc;
	
	public CollisionBox collisionBox;
	
	public Rocket(Vector3f position, Vector3f rotation, RenderMaster renderMaster, World world)
	{
		this.world = world;
		
		this.position = position;
		this.rotation = rotation;
		
		
		this.graphicsComponent = renderMaster.addModel("rocket");
		this.light = renderMaster.addLight();
		
		this.alternator = 0.0f;
		this.inc = true;
		light.setColor(new Vector3f(1,alternator,0));
		light.setRad(100);
		
		this.collisionBox = new CollisionBox(this.position, new Vector3f(2,2,2));
		
	}
	
	@Override
	public void update()
	{
		Vector3f delta = this.graphicsComponent.getTransformedVector(0, 0, 5, false);
		
		Vector3f.add(delta, this.position, this.position);

		this.graphicsComponent.setPosition(position);

		this.graphicsComponent.setRotation(rotation);

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
		Kart toRemove = null;
		
		for(Kart k : world.donutKarts)
		{
			if (this.collisionBox.bIntersects(k.collisionBox))
			{
				toRemove = k;
			}
		}
		if(toRemove != null)
		{
			toRemove.graphicsComponent.setPosition(new Vector3f(0,2000,0));
			world.donutKarts.remove(toRemove);
		}
		
	}
	
	
}

