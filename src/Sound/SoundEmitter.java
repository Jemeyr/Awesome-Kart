package Sound;

import java.nio.FloatBuffer;

import org.lwjgl.openal.AL10;


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
		
		return soundMaster.stopSource(soundCode);
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
	   *  Sets the position of the listener
	   */
	  public void setSoundPosition(int x, int y , int z){
		  
		  soundMaster.setSourcePosition(x ,y , z, soundCode);

		    
	  }
	  
	  /**
	   *  Sets the velocity of the Listener
	   */
	  public void setSoundVelocity(int x, int y , int z){
		  
		 soundMaster.setSourceVelocity(x ,y , z, soundCode);  
		  
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