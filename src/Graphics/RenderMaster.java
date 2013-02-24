package Graphics;

public interface RenderMaster {
	
	public void draw();
	
	//this adds a model, if the model is not loaded, return null
	public GraphicsComponent addModel(String s);
	public GraphicsComponent addSubModel(String s);//this doesn't add it to the render list, 
													//so it must be added as a subcomponent
	
	//this removes a model, the model remains loaded (assume few one-use models)
	public void removeModel(GraphicsComponent g);
	
	//this loads a model in advance. Use it to set up level
	public void loadModel(String s);
	public void flushUnused();
	
}
