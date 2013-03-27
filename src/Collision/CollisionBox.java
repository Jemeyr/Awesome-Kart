package Collision;

import org.lwjgl.util.vector.Vector3f;

public class CollisionBox {
	private Vector3f position;
	private Vector3f dimensions;
	
	private static Vector3f diffPos;
	private static Vector3f diffSize;
	private static Vector3f diff;
	
	public CollisionBox()
	{
		
		diffPos = new Vector3f();
		diffSize = new Vector3f();
		diff = new Vector3f();
	}
	
	public CollisionBox(Vector3f center, Vector3f dimensions)
	{
		this.position = center;
		this.dimensions = new Vector3f(dimensions.x * 0.5f,dimensions.y * 0.5f,dimensions.z * 0.5f);
	}
	
	public void setPosition(Vector3f newPos)
	{
		this.position = newPos;
	}
	
	public void addPosition(Vector3f diff)
	{
		Vector3f.add(this.position, diff, this.position);
	}
	
	public boolean bIntersects(CollisionBox other)
	{
		Vector3f.sub(this.position, other.position, diffPos);
		Vector3f.add(this.dimensions, other.dimensions, diffSize);
		
		diff.x = (Math.abs(diffPos.x) - diffSize.x);//abs diff
		if(diff.x >=0)
		{
			return false;
		}
		
		diff.z = (Math.abs(diffPos.z) - diffSize.z);
		if(diff.x >=0)
		{
			return false;
		}
		
		diff.y = (Math.abs(diffPos.y) - diffSize.y);
		if(diff.y >= 0)
		{
			return false;
		}
		
		return true;
		}
	
	public Vector3f intersects(CollisionBox other)
	{
		Vector3f.sub(this.position, other.position, diffPos);
		Vector3f.add(this.dimensions, other.dimensions, diffSize);
		
		diff.x = (Math.abs(diffPos.x) - diffSize.x);//abs diff
		if(diff.x >=0)
		{
			return null;
		}
		
		diff.z = (Math.abs(diffPos.z) - diffSize.z);
		if(diff.x >=0)
		{
			return null;
		}
		
		diff.y = (Math.abs(diffPos.y) - diffSize.y);
		if(diff.y >= 0)
		{
			return null;
		}
		
		
		
		return new Vector3f(	diff.x * Math.signum(-diffPos.x),
								diff.y * Math.signum(-diffPos.y),
								diff.z * Math.signum(-diffPos.z));
		
	}
	
	
}
