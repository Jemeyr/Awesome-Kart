package Graphics;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	protected Vector3f position;
	protected Vector3f direction;
	
	protected Matrix4f viewMat, projection;
	
	private float aspectRatio, fov, near, far;
	//should this have some for of where it's rendering to?
	
	public Camera(Vector3f pos, Vector3f target, float aspectRatio, float fov)
	{
		this.aspectRatio = aspectRatio;
		this.fov = fov;
		this.near = 0.1f;
		this.far = 10000f;

		
		this.position = pos;
		this.viewMat = buildViewMatrix(position, target);
		this.projection = buildPerspectiveMatrix(this.fov, this.aspectRatio, near, far);
		
		
		
		this.direction = new Vector3f();
		Vector3f.sub(target, position, this.direction);
	}
	
	public void setFOV(float fov)
	{
		this.fov = fov;
		this.projection = buildPerspectiveMatrix(this.fov, this.aspectRatio, this.near, this.far);
	}
	
	public void setAspectRatio(float aspectRatio)
	{
		this.aspectRatio = aspectRatio;
		this.projection = buildPerspectiveMatrix(this.fov, this.aspectRatio, this.near, this.far);
	}

	Matrix4f buildPerspectiveMatrix(float fov, float ratio, float nearP, float farP) 
	{
	    float f = 1.0f / (float)Math.tan(fov * (3.14159 / 360.0));
	 
	    Matrix4f projMatrix = new Matrix4f();
	    Matrix4f.setIdentity(projMatrix);
	 
	    projMatrix.m00 = f / ratio;
	    projMatrix.m11 = f;
	    projMatrix.m22 = (farP + nearP) / (nearP - farP);
	    projMatrix.m23 = (2.0f * farP * nearP) / (nearP - farP);
	    projMatrix.m32 = -1.0f;
	    projMatrix.m33 = 0.0f;
	    
	    
	    return projMatrix;
	}
	
	Matrix4f buildViewMatrix(Vector3f camPos, Vector3f target)
	{
		camPos = new Vector3f(0,3,5);
		target = new Vector3f(0,0,0);
		
		
		Vector3f dir,up,right;

		dir 	= new Vector3f();
		up 		= new Vector3f(0.0f, 1.0f, 0.0f);
		right 	= new Vector3f();
		
		Vector3f.sub(target, camPos, dir);
		dir.normalise();
		
		Vector3f.cross(dir, up, right);
		right.normalise();

		
		Vector3f.cross(right, dir, up);
		up.normalise();
		
		Matrix4f view = new Matrix4f();
		
		view.m00 = right.x;
		view.m10 = right.y;
		view.m20 = right.z;
		
		view.m01 = up.x;
		view.m11 = up.y;
		view.m21 = up.z;
		
		view.m02 = -dir.x;
		view.m12 = -dir.y;
		view.m22 = -dir.z;
		
		view.m33 = 1.0f;
		
		
		Matrix4f aux = new Matrix4f();
		
		aux.m30 = -camPos.x;
		aux.m31 = -camPos.y;
		aux.m32 = -camPos.z;
		
		Matrix4f result = new Matrix4f();
		Matrix4f.mul(view, aux, result);
		
		result.transpose();
		
		return result;
	}
	
	
	
}
