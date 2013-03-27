package Sound;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class ListenerComponent {
	
	private boolean isActive = false;
	private SoundMaster soundMaster = null;
	
	protected ListenerComponent(SoundMaster soundMaster, boolean isActive){
		this.soundMaster = soundMaster;
		this.isActive = isActive;
		
		
		
		  
		
	}
	
	
	/**
	   *   Sets the position of the listener by using a vector given by x, y and z
	   * 
	   * @param x
	   * @param y
	   * @param z
	   */
	  public void setListenerPosition(int x, int y , int z){
		  if(isActive)
			  this.soundMaster.setListenerPosition(x, y, z);
		  

		    
	  }
	  
	  /**
	   * Sets the velocity of the Listener by using a vector given by x, y and z
	   * 
	   * @param x 
	   * @param y
	   * @param z
	   */
	  public void setListenerVelocity(int x, int y , int z){
		  if(isActive)
			  this.soundMaster.setListenerVelocity(x, y, z);
		    
		  
	  }
	  
	 /**
	  * Sets the orientation of the Listener. 
	  * 
	  * @param x - nose vector x
	  * @param y - nose vector y
	  * @param z - nose vector z
	  */
	  public void setListenerOrientation(int x, int y , int z){
		if(isActive)
			this.soundMaster.setListenerOrientation(x, y, z);
	  }
	

}
