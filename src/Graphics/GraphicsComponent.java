package Graphics;

import org.lwjgl.util.vector.Vector3f;

public interface GraphicsComponent {
	GraphicsComponent addSubComponent(String s, RenderMaster r);
	
	public void setPosition(Vector3f v);
	public void updatePosition(Vector3f v);
	
	public void setRotation(Vector3f v);
	public void updateRotation(Vector3f v);
	
	public Vector3f getTransformedVector(float x, float y, float z, boolean absolute);
	public Vector3f getTransformedVector(Vector3f vec, boolean absolute);
	
	
	
}
