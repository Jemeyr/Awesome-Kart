package Graphics;

public class DeferredRenderMaster implements RenderMaster {

	protected DeferredRenderMaster()
	{
		
	}
	
	public void draw()
	{
		System.out.println("A good picture of some cars is drawn");
	}

	@Override
	public GraphicsComponent addModel(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeModel(GraphicsComponent g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadModel(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flushUnused() {
		// TODO Auto-generated method stub
		
	}

}
