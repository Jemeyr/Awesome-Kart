package Graphics;


import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;


public class RenderMasterFactory {
	
	public static RenderMaster getRenderMaster()
	{
	
		RenderMaster ret = null;
		
		try{
			Display.setDisplayMode(new DisplayMode(800, 600));
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
			ret = new DeferredRenderMaster(1.33f);//8/6
		}
		else
		{
			ret = new DebugRenderMaster(1.33f);
		}

		return ret;
	}
}
