package World;

import org.lwjgl.util.vector.Vector3f;

public class Checkpoint {
	protected Vector3f post;
	protected boolean isFinishLine  = false;
	
	public Checkpoint(Vector3f post)
	{
		this.post =  post;
		
	}
	
	//Sets this checkpint to be the finish checkpoint
	public void setFinishCheckpoint()
	{
		isFinishLine = true;
	}

}
