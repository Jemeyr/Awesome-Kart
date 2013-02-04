package Graphics;


import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;


public class RenderMasterFactory {
	
	public static RenderMaster getRenderMaster()
	{
	
		RenderMaster ret = null;
		
		try{
			Display.setDisplayMode(new DisplayMode(320, 240));
			Display.create();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.exit(-1);
		}
		
		
		System.out.println();
		
		if(GLContext.getCapabilities().OpenGL30)
		{
			ret = new DeferredRenderMaster();
		}
		else
		{
			ret = new DebugRenderMaster();
		}
		
		
		return ret;
	}
	
}
