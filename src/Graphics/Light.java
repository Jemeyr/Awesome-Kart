package Graphics;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Light {
	public float rad;
	public Vector3f color;
	public Vector3f pos;
	
	private Matrix4f world;
	
	public Light()
	{
		this.rad = 5f;
		this.color = new Vector3f(1,1,1);
		this.pos = new Vector3f(0,0,0);
		this.world = new Matrix4f();
		Matrix4f.translate(pos, world, world);
	}
	
	
}
