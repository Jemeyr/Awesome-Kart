package Graphics;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Light {

	protected float rad;
	protected Vector3f color;

	static protected DebugMesh mesh;
	
	private Vector3f pos;

	private Matrix4f world;
	
	static protected void init(DebugMesh m)
	{
		Light.mesh = m;
	}
	
	public Light(float rad)
	{
		this.rad = rad;
		
		this.color = new Vector3f(1,1,1);
		this.pos = new Vector3f(0,0,0);
		this.world = new Matrix4f();
		Matrix4f.translate(pos, world, world);
	}
	
	public Light()
	{
		this.rad = 5f;
		
		this.color = new Vector3f(1,1,1);
		this.pos = new Vector3f(0,0,0);
		this.world = new Matrix4f();
		Matrix4f.translate(pos, world, world);
	}

	public void setPosition(Vector3f pos)
	{
		this.pos = pos;
		this.world = new Matrix4f();
		Matrix4f.translate(pos, world, world);
	}
	
	public void addPosition(Vector3f pos)
	{
		Vector3f.add(this.pos, pos, this.pos);
		Matrix4f.translate(pos, world, world);
	}
	
	protected Matrix4f getModelMat()
	{
		return this.world;
	}
	
}
