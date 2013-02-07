package Graphics;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import org.lwjgl.util.vector.Vector3f;

public class DebugGraphicsComponent {
	
	
	private int vao;
	private Vector3f position;
	private Quaternion rotation;
	
	private Matrix4f modelMat;
	
	protected List<DebugGraphicsComponent> subComponents;
	
	protected DebugGraphicsComponent(int vao)
	{
		//This is the vao
		this.vao = vao;
		
		this.position = new Vector3f(0f,0f,0f);
		
		this.rotation = new Quaternion();
		rotation.setIdentity();
		
		this.subComponents = new LinkedList<DebugGraphicsComponent>();
		
		//set the model matrix
		this.modelMat = new Matrix4f(); 
		modelMat.setIdentity();
	}
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public void setRotation(Quaternion quat)
	{
		this.rotation = quat;
	}
	
	public void updatePosition(Vector3f position)
	{
		Vector3f.add(this.position, position, this.position);
	}
	
	public void updateRotation(Quaternion quat)
	{
		Quaternion.mul(this.rotation, quat, this.rotation);
	}
	
	public void update()
	{
		Matrix4f.setIdentity(modelMat);
		modelMat.translate(this.position);
		
		Matrix4f rot = quat2mat(this.rotation);
		
		Matrix4f.mul(rot, modelMat, rot);
		
		for(DebugGraphicsComponent gc : subComponents)
		{
			gc.update();
		}
	}
	
	
	//recursively finds all
	protected Set<Integer> getCurrentModels()
	{
		Set<Integer> ret = new HashSet<Integer>();
		ret.add(vao);
		
		for(DebugGraphicsComponent gc : subComponents)
		{
			ret.addAll(gc.getCurrentModels());
		}
		
		return ret;
	}
	
	
	
	//put this in a util class maybe?
	private static Matrix4f quat2mat(Quaternion q)
	{
		Matrix4f ret = new Matrix4f();
		float x = q.x;
		float y = q.y;
		float z = q.z;
		float w = q.w;
		
		ret.m00 = 1 - 2 * (y*y - z*z); 
		ret.m01 = 2 * (x*y + w*z);
		ret.m02 = 2 * (x*z - w*y);
		ret.m03 = 0;
		
		ret.m10 = 2 * (x*y - w*z);
		ret.m11 = 1 - 2 * (x*x - z*z);
		ret.m12 = 2 * (y*z + w*x);
		ret.m13 = 0;
		
		ret.m20 = 2 * (x*z + w*y);
		ret.m21 = 2 * (y*z - w*x);
		ret.m22 = 1 - 2 * (x*x - y*y);
		ret.m23 = 0;
		
		ret.m30 = 0;
		ret.m31 = 0;
		ret.m32 = 0;
		ret.m33 = 1;
		
		return ret;
	}
	
	
}
