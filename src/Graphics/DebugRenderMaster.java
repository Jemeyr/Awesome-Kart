package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class DebugRenderMaster implements RenderMaster {

	private List<DebugGraphicsComponent> graphicsComponents;
	private List<DebugMesh >loadedModels;
	
	
	public Camera cam;
	
	private Shader shader;
	
	protected DebugRenderMaster(float aspect)
	{
		//freedee
        glEnable(GL_DEPTH_TEST);
        glClearColor(0f, 0f, 0f, 1f);
        
        shader = new Shader();
		
		this.cam = new Camera(new Vector3f(0f,3f, 5f), new Vector3f(0f,0f,0f), aspect,60.0f);
		
		System.out.println("DebugRenderMaster: created shader and camera");
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
				graphicsComponents.add(ret);
				return ret;
			}
		}
		
		//you haven't loaded the model
		return null;
	}
	
	
	
	public void draw()
	{

    	
		//how to draw, iterate over all the graphics components and draw their parts
		shader.begin();
		shader.useCam(cam);
		
		for(DebugGraphicsComponent gc : graphicsComponents)
		{
			shader.draw(gc);
		}
		
		
		shader.end();
		
		Display.update();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
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
		loadedModels.add(new DebugMesh(s,shader));
		
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
