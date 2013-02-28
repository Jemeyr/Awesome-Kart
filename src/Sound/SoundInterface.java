package Sound;


/**
 * Interface class that controls how game objects will get the soundMaster to play sounds
 * 
 * 
 * @author Daniel
 *
 */
public class SoundInterface {
	
	private SoundMaster soundMaster;
	
	public SoundInterface(SoundMaster object)
	{
		soundMaster = object;
	}
	
	
	/**
	 * Creates a sound within the soundmaster 
	 * 
	 * @return A sound id that is used to play the sound
	 */
	public int createSound()
	{
		
		
		return 0;
	}
	
	/**
	 * 
	 * @param soundCode - the sound's id
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean playSoundOnce(int soundCode)
	{
	
		
		return false;
	}
	
	/**
	 * 
	 * @param soundCode - the sound's id
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean playSoundLoop(int soundCode)
	{
		
	
		return false;
	}
	
	

}
