package World;

import org.lwjgl.util.vector.Vector3f;

import Graphics.GraphicsComponent;
import Graphics.RenderMaster;

public class Rocket {
	public Player owner;
	public Vector3f position;
	public Vector3f rotation;
	
	private GraphicsComponent graphicsComponent;
	
	public Rocket(Vector3f position, Vector3f rotation, RenderMaster renderMaster)
	{
		this.position = position;
		this.rotation = rotation;
		
		this.graphicsComponent = renderMaster.addModel("rocket");
	}
	
	public void update()
	{
		Vector3f delta = this.graphicsComponent.getTransformedVector(0, 0, 1, false);
		
		Vector3f.add(delta, this.position, this.position);
		
		this.graphicsComponent.setPosition(position);
		
		
		
	}
	
	
}

