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

	public void setColor(Vector3f col)
	{
		this.color = col;
	}
	
	public Vector3f getColor()
	{
		return this.color;
	}
	
	public void setPosition(Vector3f pos)
	{
		this.pos = pos;
		this.world.setIdentity();
		
		//translate
		this.world.m03 = this.pos.x;
		this.world.m13 = this.pos.y;
		this.world.m23 = this.pos.z;
		
	}
	
	public Vector3f getPosition()
	{
		return this.pos;
	}
	
	public void setRad(float f)
	{
		this.rad = f;
	}
	
	public void addPosition(Vector3f pos)
	{
		Vector3f.add(this.pos, pos, this.pos);
		
		this.world.setIdentity();
		
		//translate
		this.world.m03 = this.pos.x;
		this.world.m13 = this.pos.y;
		this.world.m23 = this.pos.z;
		
	}
	
	protected Matrix4f getModelMat()
	{
		return this.world;
	}
	
}
