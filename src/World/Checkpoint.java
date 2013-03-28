package World;

import org.lwjgl.util.vector.Vector3f;

public class Checkpoint {
	protected Vector3f post;
	protected boolean isFinishLine  = false;
	public int pointNum = 0;
	public float rotation;
	
	public Checkpoint(Vector3f post, int num, float rot)
	{
		this.post =  post;
		pointNum = num;
		this.rotation = rot;
		
	}
	
	//Sets this checkpint to be the finish checkpoint
	public void setFinishCheckpoint()
	{
		isFinishLine = true;
	}

}
