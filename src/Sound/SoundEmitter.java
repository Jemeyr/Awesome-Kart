package Sound;


/**
 * Emitter object that a is recieved from the sound Master when a game component wants a sound
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
	 * 
	 * 
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean playSound()
	{
		
		return soundMaster.playSound(soundCode);
	}
	
	/**
	 * Stops the sound
	 * 
	 * @return true if success
	 * 		   False on failure	
	 */
	public boolean stopSound()
	{
		
		return soundMaster.stopSound(soundCode);
		
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
	
	public void destroySound()
	{
		
		
	}
	
	

}