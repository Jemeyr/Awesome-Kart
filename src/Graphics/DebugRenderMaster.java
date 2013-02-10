package Graphics;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.*;

import org.lwjgl.util.vector.Vector3f;

public class DebugRenderMaster implements RenderMaster {

	private List<DebugGraphicsComponent> graphicsComponents;
	private List<DebugMesh >loadedModels;
	
	private Camera cam;
	
	private Material m;
	
	protected DebugRenderMaster(float aspect)
	{
		//freedee
        glEnable(GL_DEPTH_TEST);
        
        m = new Material();
		
		this.cam = new Camera(new Vector3f(0f,3f, 10f), new Vector3f(0f,0f,0f), aspect,60.0f);
		
		this.graphicsComponents = new ArrayList<DebugGraphicsComponent>();
		this.loadedModels = new ArrayList<DebugMesh>();
	}
	
	public void removeModel(GraphicsComponent g)
	{
		this.graphicsComponents.remove(g);
	}
	
	
	public GraphicsComponent addModel(String id)
	{
		
		for(DebugMesh dm : loadedModels)
		{
			if(dm.id.equals(id))
			{
				DebugGraphicsComponent ret = new DebugGraphicsComponent(dm);
				return (GraphicsComponent) ret;
			}
		}
		
		//you haven't loaded the model
		return null;
	}
	
	
	
	public void draw()
	{
		//how to draw, iterate over all the graphics components and draw their parts
		for(DebugGraphicsComponent gc : graphicsComponents)
		{
			m.draw(cam, gc);
		}
		
		
		
		System.out.println("An ugly picture of some cars is drawn");
	}

	public void loadModel(String s) {
		//if the loaded models already has the id, just use that.
		for(DebugMesh dm : loadedModels)
		{
			if(dm.id.equals(s))
			{
				return;
			}
		}
		loadedModels.add(new DebugMesh(s));
		
	}

	public void flushUnused() {
		
		//find all the used vaos
		Set<DebugMesh> meshes = new HashSet<DebugMesh>();
		
		for(DebugGraphicsComponent gc : graphicsComponents)
		{
			meshes.addAll(gc.getCurrentModels());
		}
		//take every vao and remove the ones which are still used
		Set<DebugMesh> toRemove = new HashSet<DebugMesh>(loadedModels);
		toRemove.removeAll(meshes);
		
		//remove
		for(DebugMesh i : toRemove)
		{
			i.destroy();
			loadedModels.remove(i);
		}
		
	}


}
