package Graphics;

import java.util.*;

public class DebugRenderMaster implements RenderMaster {

	private List<DebugGraphicsComponent> graphicsComponents;
	private HashMap<String, Integer> loadedModels;
	
	protected DebugRenderMaster()
	{
		this.graphicsComponents = new ArrayList<DebugGraphicsComponent>();
		this.loadedModels = new HashMap<String, Integer>();
	}
	
	public void removeModel(GraphicsComponent g)
	{
		this.graphicsComponents.remove(g);
	}
	
	
	public GraphicsComponent addModel(String resourceName)
	{
		Integer vaoIndex = loadedModels.get(resourceName);
		
		if(vaoIndex == null)
		{
			return null;
		}
		
		DebugGraphicsComponent ret = new DebugGraphicsComponent(vaoIndex);
		
		return (GraphicsComponent)ret;
	}
	
	
	
	public void draw()
	{
		System.out.println("An ugly picture of some cars is drawn");
	}

	@Override
	public void loadModel(String s) {
		loadedModels.put(s, ModelLoader.loadModel(s));
	}

	public void flushUnused() {
		
		//find all the used vaos
		Set<Integer> vaos = new HashSet<Integer>();
		
		for(DebugGraphicsComponent gc : graphicsComponents)
		{
			vaos.addAll(gc.getCurrentModels());
		}
		//take every vao and remove the ones which are still used
		Set<Integer> toRemove = new HashSet<Integer>(loadedModels.values());
		toRemove.removeAll(vaos);
		
		//remove
		for(Integer i : toRemove)
		{
			loadedModels.remove(i);
		}
		
	}


}
