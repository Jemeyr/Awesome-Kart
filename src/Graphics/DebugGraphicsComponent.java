package Graphics;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class DebugGraphicsComponent implements GraphicsComponent {
	
	protected DebugMesh mesh;
	private Vector3f position;
	private Vector3f rotation;//yaw pitch roll
	
	private Matrix4f parentMat;
	
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
		
		this.parentMat = new Matrix4f(); 
		parentMat.setIdentity();
	}
	
	
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
		update();
	}
	
	public void setRotation(Vector3f rot)
	{
		this.rotation = rot;
		update();
	}
	
	public void updatePosition(Vector3f position)
	{
		Vector3f.add(this.position, position, this.position);
		update();
	}
	
	public void updateRotation(Vector3f rot)
	{
		Vector3f.add(this.rotation, rot, this.rotation);
		update();
	}
	
	protected Matrix4f getModelMat()
	{
		return this.modelMat;
	}
	
	public Matrix4f getInvModelMat()
	{
		Matrix4f ret = new Matrix4f();
		Matrix4f.invert(this.modelMat, ret);
		//ret = this.modelMat;
		return ret;
	}
	
	public Matrix4f DEBUGgetModelMat()
	{
		return this.modelMat;
	}
	
	public void setParentMat(Matrix4f parent)
	{
		this.parentMat = parent;
	}
	
	public void update()
	{
		
		
		Matrix4f trans = new Matrix4f();
		trans.setIdentity();
		
		//translate
		trans.m03 += position.x;
		trans.m13 += position.y;
		trans.m23 += position.z;
		
		
		Matrix4f yaw = new Matrix4f();
		Matrix4f pitch = new Matrix4f();
		Matrix4f roll = new Matrix4f();

		pitch.rotate(rotation.x, new Vector3f(1,0,0));
		yaw.rotate(-rotation.y, new Vector3f(0,1,0));
		roll.rotate(rotation.z, new Vector3f(0,0,1));
		
		
		
		this.modelMat.setIdentity();
		
		
		
		Matrix4f.mul(modelMat, yaw, modelMat);
		Matrix4f.mul(modelMat, pitch, modelMat);
		Matrix4f.mul(modelMat, roll, modelMat);
		

		Matrix4f.mul(modelMat, trans, modelMat);
		
		//parent matrix
		Matrix4f.mul(modelMat, parentMat, modelMat);
		
		
		
		
		
		
		for(DebugGraphicsComponent gc : subComponents)
		{
			gc.setParentMat(this.modelMat);
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



	
	public GraphicsComponent addSubComponent(String s, RenderMaster r) {
		
		GraphicsComponent gc = r.addSubModel(s);
		
		this.subComponents.add((DebugGraphicsComponent)gc);
		
		return gc;
	}
	
	
	
	
}
