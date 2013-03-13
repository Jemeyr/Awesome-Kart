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
	
	/** boolean value to signify if there was a critical error in setting up the Open Al audio engine */
	public boolean audioEngineOperational = false;
	
	/** Maximum Data Buffers and emissions */
	public static final int NUM_BUFFERS = 10;
	public static final int NUM_SOURCES = 128;
	
	/** indexes of sounds*/
	
	public static final int aCivMusic = 0;
	public static final int carAcceleration = 1;
	public static final int pewPew = 2;
	
	/**Hash table to associate the audio sources with their position in the bufffer */
	Hashtable SoundValues =null; 
	/** Buffers hold sound data. */
	protected IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);;
  
	/** Sources are points emitting sound. */
	protected IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);;
	
	  /** Position of the source sound. */
	protected  FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

	  /** Velocity of the source sound. */
	protected  FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
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
	  
	
	  protected int addSource(int soundID, boolean toLoop)
	  {
		  int position = source.position();
		  source.limit(position + 1);
		  AL10.alGenSources(source);

		  if (AL10.alGetError() != AL10.AL_NO_ERROR)
			  return -1;

		  AL10.alSourcei(source.get(position), AL10.AL_BUFFER,   buffer.get(soundID) );
		  AL10.alSourcef(source.get(position), AL10.AL_PITCH,    1.0f          );
		  AL10.alSourcef(source.get(position), AL10.AL_GAIN,     1.0f          );
		  AL10.alSource (source.get(position), AL10.AL_POSITION, sourcePos);
		  AL10.alSource (source.get(position), AL10.AL_VELOCITY, sourceVel);
		  AL10.alSourcei(source.get(position), AL10.AL_LOOPING,  (toLoop ? AL10.AL_TRUE : AL10.AL_FALSE));
		  
		  if (AL10.alGetError() != AL10.AL_NO_ERROR)
			  return -1;
		  
		  source.position(position+1);
		  
		  return position;
		  
	  }
	  
	  public int addSound(String soundName, int soundID , boolean toLoop)
	  {
		  int soundCode = -1;
		  if(SoundValues.size()<NUM_SOURCES)
		  {
			  //There is still space for sound sources
			  
			  soundCode = addSource(soundID, toLoop);
			  if(soundCode==-1)
			  {
				  //Error adding sound
				  return soundCode;
			  }
			  
			  SoundValues.put(soundName, soundCode);
			  
			  
		  }
		  else
		  {
			  return -1;
		  }
		  
		  return soundCode;
	  }
	  
	
	  
	  public boolean playSound(String soundID){
		  
		  Object obj = SoundValues.get(soundID);
		  
		  if(obj != null)
		  {
			  
			  int key = (Integer) obj;
			  
			  return playSound(key);
			  
		  }
		  return false;
	  }
	  
	  public boolean playSound(int soundCode){
		  int state = AL10.alGetSourcei(source.get(soundCode), AL10.AL_SOURCE_STATE);
		  String errorString;
		  int errCode;
			
		  if(state != AL10.AL_PLAYING)
		  {
			  AL10.alSourcePlay(source.get(soundCode));
			  
			  if((errCode=loadAllSoundData()) == AL10.AL_FALSE)
			  {
				  errorString= getALErrorString(errCode);
				  System.out.println(errorString);
				  return false;
			  
			  }
		  }
		  
		  return true;

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
	   * boolean LoadALData()
	   *
	   *  This function will load our all audio data into a buffer so that sources can be created for the game
	   *  
	   *  Returns 1 on Success
	   *  Returns 0 on Fail
	   */
	  protected int loadAllSoundData() {
		    
		  InputStream is = null;
		  InputStream bufferedIs = null;
		  WaveData waveFile= null;
		  
		  AL10.alGenBuffers(buffer);
		  
			try {
				is = new FileInputStream("assets/sound/ACiv Battle 2.wav");
				bufferedIs = new BufferedInputStream(is);
				waveFile = WaveData.create(AudioSystem.getAudioInputStream(bufferedIs));
				
				AL10.alBufferData(buffer.get(aCivMusic), waveFile.format, waveFile.data, waveFile.samplerate);	  
				waveFile.dispose();
				//is = new FileInputStream("./assets/sound/FancyPants.wav");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			try {
				is = new FileInputStream("assets/sound/Car Accelerating.wav");
				bufferedIs = new BufferedInputStream(is);
				waveFile = WaveData.create(AudioSystem.getAudioInputStream(bufferedIs));
				
				AL10.alBufferData(buffer.get(carAcceleration), waveFile.format, waveFile.data, waveFile.samplerate);	  
				waveFile.dispose();
				//is = new FileInputStream("./assets/sound/FancyPants.wav");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			try {
				is = new FileInputStream("assets/sound/Pew_Pew.wav");
				bufferedIs = new BufferedInputStream(is);
				waveFile = WaveData.create(AudioSystem.getAudioInputStream(bufferedIs));
				
				AL10.alBufferData(buffer.get(pewPew), waveFile.format, waveFile.data, waveFile.samplerate);	  
				waveFile.dispose();
				//is = new FileInputStream("./assets/sound/FancyPants.wav");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		  
		  
		  
		  // Do another error check and return.
		  if (AL10.alGetError() == AL10.AL_NO_ERROR)
		      return AL10.AL_TRUE;

		  return AL10.AL_FALSE;
		  
	  
	  }
	  
	  
	  /**
	   * public void execute()
	   * 
	   */
	  
	  public void execute(){
		  
		SoundValues = new Hashtable();
		String errorString;
		int errCode;
		// Initialize the buffers for audio data
		
		
	  	// CRUCIAL!
	    // any buffer that has data added, must be flipped to establish its position and limits
	    sourcePos.flip();
	    sourceVel.flip();
	    listenerPos.flip();
	    listenerVel.flip();
	    listenerOri.flip();
		
	    /*
		//Fixing some error casae
		sourcePos.position(0);
		sourceVel.position(0);
		listenerPos.position(0);
		listenerVel.position(0);
		listenerOri.position(0);
		*/
		  
		  try{
			AL.create();  
		  }
		  catch(LWJGLException le){
			le.printStackTrace();
			return;
		  }
		  
		  
		  
		  if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
		  {
			  audioEngineOperational = false;
			  errorString= getALErrorString(errCode);
			  System.out.println(errorString);
			  return;
		  }
		  else
			  audioEngineOperational = true;
			  
		  
		  
		  if((errCode=loadAllSoundData()) == AL10.AL_FALSE)
		  {
			  audioEngineOperational = false;
			  errorString= getALErrorString(errCode);
			  System.out.println(errorString);
			  return;
		  
		  }
		  else
			  audioEngineOperational = true;
		  
		  setListenerValues();
		  
		  
	  }
	  
	

}
