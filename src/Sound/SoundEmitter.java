package Sound;

import java.nio.FloatBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;


/**
 * This class is used to simplify the playing of sounds
 * <p>
 * An instance of this class is returned by the soundMaster when the user wants a source to be created.
 * <p>
 * This source can now be interacted with, by using this classes simple methods such as playSound() and stopSound()
 * 
 * 
 * @author Daniel
 *
 */
public class SoundEmitter {
	
	private SoundMaster soundMaster;
	private String soundName;
	private int soundCode;
	
	/** Boolean representing if this sound is active, if this is false then an error has occured, possibly when creating the sound
	 * 	or the sound was destroyed at some point
	 *  */
	public boolean active = false;
	
	protected SoundEmitter(SoundMaster object, String fileName, boolean toLoop)
	{
		soundMaster = object;
		soundName = fileName;
		int x  = (Integer)soundMaster.soundIndexes.get(fileName);
		soundCode = soundMaster.addSource((Integer) soundMaster.soundIndexes.get(fileName), toLoop);
		
		//no failures while adding the source
		if(soundCode!= -1)
		{
			this.soundCode = soundCode;
			active = true;
		
		}
	}
	
	
	/**
	 * Plays the sound
	 * 
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean playSound()
	{
		
		return soundMaster.playSource(soundCode);
	}
	
	/**
	 * Stops the sound
	 * 
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean stopSound()
	{
		
		return soundMaster.stopSource(soundCode);
		
	}
	
	public boolean pauseSound(){
		return soundMaster.pauseSource(soundCode);
		
	}
	
	/** Sets the Sound to loop
	 * 
	 * 
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean setSoundLoop()
	{
		return soundMaster.setLooping(soundCode, true);
		
	}
	
	/**Sets the sound to play only once
	 * 
	 * 
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean setSoundSingle()
	{
		return soundMaster.setLooping(soundCode, false);
		
	}
	
	/**
	 * Sets the gain of a sound, 
	 * 
	 * min is 0 
	 * 
	 * default is 1
	 * @return
	 */
	public void setSoundGain(float gain)
	{
		soundMaster.setSourceGain(soundCode, gain);
		return ;
	}
	  /**
	   *  Sets the position of the listener
	   */
	  public void setSoundPosition(float x, float y , float z){
		  
		  soundMaster.setSourcePosition(x ,y , z, soundCode);

		    
	  }
	  
	  /**
	   *  Sets the position of the listener
	   */
	  public void setSoundPosition(Vector3f vector){
		  
		  soundMaster.setSourcePosition(vector.getX() ,vector.getY() , vector.getZ(), soundCode);
    
	  }
	  
	  /**
	   *  Sets the velocity of the Listener
	   */
	  public void setSoundVelocity(float x, float y , float z){
		  
		 soundMaster.setSourceVelocity(x ,y , z, soundCode);  
		  
	  }
	  
	  /**
	   * Sets the reference distance of this sound object
	   * 
	   * The reference distance is how fast the sound is attuated or
	   * from how far off a sound can be heard, ie a footstep may have 
	   * a ref distance of 2 and the vol will fall off very quick
	   * while an explosion may have a ref distance of 50, will fall 
	   * off very slow
	   * 
	   * the default is 1024
	   * 
	   * @param refDistance
	   */
	  public void setReferenceDistance(float refDistance){
		  
		  soundMaster.setRefDistance(soundCode, refDistance);
	  }
	
	 /**
	  * Removes the sound from the audio engine and deactivates this object
	  * 
	  * After this, this object will no longer server a purpose, and thus should be destroyed
	  */
	public void removeSound()
	{
		soundMaster.removeSource(soundCode);
		
		this.active = false;
		
	}
	
	

}