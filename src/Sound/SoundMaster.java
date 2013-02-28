package Sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;


public class SoundMaster {
	
	/** Maximum Data Buffers and emissions */
	public static final int NUM_BUFFERS = 10;
	public static final int NUM_SOURCES = 10;
	
	/**Hash table to associate the audio sources with their position in the bufffer */
	Hashtable SoundValues =null; 
	/** Buffers hold sound data. */
	protected IntBuffer buffer ;
  
	/** Sources are points emitting sound. */
	protected IntBuffer source ;
	
	 /** Position of the source sounds. */
	protected FloatBuffer sourcePos ;
	
	  /** Velocity of the source sounds. */
	protected FloatBuffer sourceVel ;
	
	  /** Position of the listener. */
	protected FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
	  /** Velocity of the listener. */
	protected FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
	  /** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	protected FloatBuffer listenerOri =
	      BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f });
	
	/**
	   * 1) Identify the error code.
	   * 2) Return the error as a string.
	   */
	  public static String getALErrorString(int err) {
	    switch (err) {
	      case AL10.AL_NO_ERROR:
	        return "AL_NO_ERROR";
	      case AL10.AL_INVALID_NAME:
	        return "AL_INVALID_NAME";
	      case AL10.AL_INVALID_ENUM:
	        return "AL_INVALID_ENUM";
	      case AL10.AL_INVALID_VALUE:
	        return "AL_INVALID_VALUE";
	      case AL10.AL_INVALID_OPERATION:
	        return "AL_INVALID_OPERATION";
	      case AL10.AL_OUT_OF_MEMORY:
	        return "AL_OUT_OF_MEMORY";
	      default:
	        return "No such error code";
	    }
	  }
	  

	/**
	 * initalizeBuffers()
	 * 
	 * Initializes all the sound buffers so that audio data can be stored. 
	 */
	protected void initalizeBuffers()
	{
		buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
		  
		source = BufferUtils.createIntBuffer(NUM_BUFFERS);
		
		sourcePos = BufferUtils.createFloatBuffer(3*NUM_BUFFERS);
		
		sourceVel = BufferUtils.createFloatBuffer(3*NUM_BUFFERS);
		
		
		
		//Fixing some error casae
		  sourcePos.position(0);
		  sourceVel.position(0);
		  listenerPos.position(0);
		  listenerVel.position(0);
		  listenerOri.position(0);
		
	}
	
	  /**
	   * boolean LoadALData()
	   *
	   *  This function will load our sample data from the disk using the Alut
	   *  utility and send the data into OpenAL as a buffer. A source is then
	   *  also created to play that buffer.
	   *  
	   *  Returns 1 on Success
	   *  Returns 0 on Fail
	   */
	  protected int loadSoundData(String fileName, int position, boolean toLoop) {
		    
		  InputStream is = null;
		  InputStream bufferedIs = null;
		  WaveData waveFile= null;
		  
		  AL10.alGenBuffers(buffer);
		  
			try {
				is = new FileInputStream(fileName);
				bufferedIs = new BufferedInputStream(is);
				
				
				waveFile = WaveData.create(AudioSystem.getAudioInputStream(bufferedIs));
			
				//is = new FileInputStream("./assets/sound/FancyPants.wav");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		  
		  AL10.alBufferData(buffer.get(position), waveFile.format, waveFile.data, waveFile.samplerate);
		  
		  waveFile.dispose();
		  
		  AL10.alGenSources(source);

		  if (AL10.alGetError() != AL10.AL_NO_ERROR)
			  return AL10.AL_FALSE;

		  AL10.alSourcei(source.get(position), AL10.AL_BUFFER,   buffer.get(position) );
		  AL10.alSourcef(source.get(position), AL10.AL_PITCH,    1.0f          );
		  AL10.alSourcef(source.get(position), AL10.AL_GAIN,     1.0f          );
		  AL10.alSource (source.get(position), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(position*3));
		  AL10.alSource (source.get(position), AL10.AL_VELOCITY, (FloatBuffer) sourceVel.position(position*3));
		  AL10.alSourcei(source.get(position), AL10.AL_LOOPING,  (toLoop ? AL10.AL_TRUE : AL10.AL_FALSE));
		  
		  // Do another error check and return.
		  if (AL10.alGetError() == AL10.AL_NO_ERROR)
		      return AL10.AL_TRUE;

		  return AL10.AL_FALSE;
		  
	  
	  }
	  
	  /**
	   * void setListenerValues()
	   *
	   *  We already defined certain values for the Listener, but we need
	   *  to tell OpenAL to use that data. This function does just that.
	   */
	  protected void setListenerValues() {
	    AL10.alListener(AL10.AL_POSITION,    listenerPos);
	    AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
	    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	  }
	  
	  /**
	   * public void execute()
	   * 
	   */
	  
	  public void execute(){
		  
		SoundValues = new Hashtable();
		  
		// Initialize the buffers for audio data
		
		initalizeBuffers();
		  
		  
		  try{
			AL.create();  
		  }
		  catch(LWJGLException le){
			le.printStackTrace();
			return;
		  }
		  
		  
		  
		  AL10.alGetError();
		  
		  
		  
	  }
	  
	  public boolean playSound(String soundID){
		  
		  Object obj = SoundValues.get(soundID);
		  
		  if(obj != null)
		  {
			  
			  int key = (Integer) obj;
			  
			  playSound(key);
			  
		  }
		  return false;
	  }
	  
	  public void playSound(int soundCode){
		  int state = AL10.alGetSourcei(source.get(soundCode), AL10.AL_SOURCE_STATE);
		  
		  if(state != AL10.AL_PLAYING)
		  {
			  AL10.alSourcei(source.get(soundCode), AL10.AL_BUFFER,   buffer.get(soundCode) );
			  AL10.alSourcePlay(source.get(soundCode));
		  }

		  /*while(state == AL10.AL_PLAYING){
			  System.out.println("Playing audio");
			  try {
				Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("sleep failed");
					e.printStackTrace();
				}
			  state = AL10.alGetSourcei(source.get(soundCode), AL10.AL_SOURCE_STATE);
		  }
		  
		  System.out.println("done playing audio");
		  */
	  }
	  
	  public int addSound(String soundName, boolean toLoop)
	  {
		  int soundCode = -1;
		  if(SoundValues.size()<NUM_SOURCES)
		  {
			  //There is still space for sounds
			  
			  soundCode = SoundValues.size();
			  
			  SoundValues.put(soundName, SoundValues.size());
			  
			  
		  }
		  else
		  {
			  return -1;
		  }
		  
		  if(loadSoundData(soundName,soundCode, toLoop)==AL10.AL_FALSE){
			System.out.println("Error Loading data.");
			SoundValues.remove(soundName);
			return -1;
		  }
		  
		  setListenerValues();
		  
		  return soundCode;
	  }
	  
	  /**
	   * void killALData()
	   *
	   *  We have allocated memory for our buffers and sources which needs
	   *  to be returned to the system. This function frees that memory.
	   */
	  public void cleanUpALData() {
		  
		SoundValues.clear();
		
	    AL10.alDeleteSources(source);
	    AL10.alDeleteBuffers(buffer);
	    AL.destroy();
	  }
	  
	

}
