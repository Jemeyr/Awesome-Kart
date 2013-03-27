package World;

import org.lwjgl.util.vector.Vector3f;

import Graphics.GraphicsComponent;
import Graphics.Light;
import Graphics.RenderMaster;

public class Rocket {
	public Player owner;
	public Vector3f position;
	public Vector3f rotation;
	
	private GraphicsComponent graphicsComponent;
	private Light light;
	
	private float alternator;
	private boolean inc;
	
	public Rocket(Vector3f position, Vector3f rotation, RenderMaster renderMaster)
	{
		this.position = position;
		this.rotation = rotation;
		
		this.graphicsComponent = renderMaster.addModel("rocket");
		this.light = renderMaster.addLight();
		
		this.alternator = 0.0f;
		this.inc = true;
		light.setColor(new Vector3f(1,alternator,0));
		light.setRad(150);
	}
	
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
		
	}
	
	
}

