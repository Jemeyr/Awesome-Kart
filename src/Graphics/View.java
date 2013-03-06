package Graphics;

import org.lwjgl.util.Rectangle;

public class View {
	protected Rectangle rect;
	protected Camera cam;
	
	public View(Rectangle r, Camera c)
	{
		this.rect = r;
		this.cam = c;
	}
	
	
	
}
