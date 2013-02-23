package Graphics;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import org.lwjgl.util.vector.Vector3f;

public class DebugGraphicsComponent implements GraphicsComponent {
	
	protected DebugMesh mesh;
	private Vector3f position;
	private Vector3f rotation;//yaw pitch roll
	
	private Matrix4f modelMat;
	
	protected List<DebugGraphicsComponent> subComponents;
	
	protected DebugGraphicsComponent(DebugMesh mesh)
	{
		//This is the vao
		this.mesh = mesh;
		
		//load the mesh
		
		this.position = new Vector3f(0f,0f,0f);
		this.rotation = new Vector3f();
		
		
		this.subComponents = new LinkedList<DebugGraphicsComponent>();
		
		//set the model matrix
		this.modelMat = new Matrix4f(); 
		modelMat.setIdentity();
	}
	
	
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public void setRotation(Vector3f rot)
	{
		this.rotation = rot;
	}
	
	public void updatePosition(Vector3f position)
	{
		Vector3f.add(this.position, position, this.position);
	}
	
	public void updateRotation(Vector3f rot)
	{
		Vector3f.add(this.rotation, rot, this.rotation);
	}
	
	protected Matrix4f getModelMat()
	{
		update();
		return this.modelMat;
	}
	
	public void update()
	{
		
		Matrix4f temp = new Matrix4f();
		temp.rotate(this.rotation.x, new Vector3f(0,1,0));
		temp.rotate(this.rotation.y, new Vector3f(1,0,0));
		temp.rotate(this.rotation.z, new Vector3f(0,0,1));
		
		//System.out.println("temp is");
		//System.out.println(temp);
		

		temp.m03 += position.x;
		temp.m13 += position.y;
		temp.m23 += position.z;
		
		
		//System.out.println("temp is now ");
		//System.out.println(temp);
		
		
		this.modelMat = temp;

		
		
		for(DebugGraphicsComponent gc : subComponents)
		{
			gc.update();
		}
	}
	
	
	//recursively finds all
	protected Set<DebugMesh> getCurrentModels()
	{
		Set<DebugMesh> ret = new HashSet<DebugMesh>();
		ret.add(mesh);
		
		for(DebugGraphicsComponent gc : subComponents)
		{
			ret.addAll(gc.getCurrentModels());
		}
		
		return ret;
	}
	
	
	
	
}
