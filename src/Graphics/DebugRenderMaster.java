package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector3f;

public class DebugRenderMaster implements RenderMaster {

	private List<DebugGraphicsComponent> graphicsComponents;
	private List<DebugMesh >loadedModels;
	
	private List<View> views;
	
	private float aspect;
	
	private GeometryShader geoShader;
	private ViewShader viewShader;
	
	
	protected DebugRenderMaster(float aspect)
	{
		//freedee
        glEnable(GL_DEPTH_TEST);
        glClearColor(0f, 0f, 0f, 1f);
        
        this.geoShader = new GeometryShader();
		
        this.viewShader = new ViewShader();
        
        this.aspect = aspect;
		
        
        this.views = new LinkedList<View>();

        //System.out.println("DebugRenderMaster: created shader and camera");
		
		this.graphicsComponents = new ArrayList<DebugGraphicsComponent>();
		this.loadedModels = new ArrayList<DebugMesh>();

	}
	
	public void removeModel(GraphicsComponent g)
	{
		this.graphicsComponents.remove(g);
	}
	
	
	public Camera addView(Rectangle r)
	{
		
		Camera c = new Camera(new Vector3f(0f,1f, 1f), new Vector3f(0f,0f,0f), aspect,60.0f);
		this.views.add(new View(r,c));
		
		return c;
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
		//this should draw each object for each view, then draw the view to screen on a quad with a simple shader
    	for(View v : views)
    	{
    		v.setRenderTarget();//TODO make this choose the target
    		
			//how to draw, iterate over all the graphics components and draw their parts
			geoShader.begin();
			
			geoShader.useCam(v.cam);
			
			for(DebugGraphicsComponent gc : graphicsComponents)
			{
				geoShader.draw(gc);
			}
			
			geoShader.end();
			
			v.unsetRenderTarget();
			
			//render the lights here
			
			viewShader.begin();
			viewShader.draw(v);
			viewShader.end();
			
    	}
    	
    	Display.sync(60);
		Display.update();
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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

		loadedModels.add(new DebugMesh(s,geoShader));
		
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

	
	public GraphicsComponent addSubModel(String id) 
	{
		

		for(DebugMesh dm : loadedModels)
		{
			if(dm.id.equals(id))
			{
				DebugGraphicsComponent ret = new DebugGraphicsComponent(dm);
				return ret;//is not added to the list of rendering models, implicitly rendered by supermodel
			}
		}
		
		//you haven't loaded the model
		return null;
	}


}
