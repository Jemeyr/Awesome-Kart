package Graphics;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	public float rad;
	public Vector3f color;
	public Vector3f pos;
	
	public Light()
	{
		this.rad = 5f;
		this.color = new Vector3f(1,1,1);
		this.pos = new Vector3f(0,0,0);
	}
}
