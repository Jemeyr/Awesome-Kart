package Sound;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

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
	  public void setListenerPosition(float x, float y , float z){
		  if(isActive)
			  this.soundMaster.setListenerPosition(x, y, z);
		  

		    
	  }
	  
		/**
	   *   Sets the position of the listener by using a vector given by x, y and z
	   * 
	   * @param x
	   * @param y
	   * @param z
	   */
	  public void setListenerPosition(Vector3f vector){
		  if(isActive)
			  this.soundMaster.setListenerPosition(vector.getX() ,vector.getY() , vector.getZ());
		  

		    
	  }
	  
	  /**
	   * Sets the velocity of the Listener by using a vector given by x, y and z
	   * 
	   * @param x 
	   * @param y
	   * @param z
	   */
	  public void setListenerVelocity(float x, float y , float z){
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
	  public void setListenerOrientation(float x,float y ,float  z){
		if(isActive)
			this.soundMaster.setListenerOrientation(x, y, z);
	  }
	  
	  /**
	   * Deactivates this component by setting isActive to false
	   * 
	   * in addition if isActive is true, it sets soundMasters listenerComponentExists to false
	   * so that additional listener components can be made
	   * 
	   */
	  public void deactivateComponent (){
		  if(isActive)
			  this.soundMaster.listenerComponentExists = false;
		  
		  isActive = false;
		  
	  }
	

}
