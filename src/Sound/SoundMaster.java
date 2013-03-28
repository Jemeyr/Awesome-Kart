package Sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
*  Class for the sound engine
*  <p>
*  To add a new sound file, in the constructor add a new entry to the hash table soundIndex with the key being the 
*  	filename complete with path and then a new interger counting up from the previous soundIndexes entry.
*  	see the examples below
*  <p>
*  As it is at this point the sound master can hold NUM_BUFFERS worth of sound files and NUM_SOURCES worth of sources
*  <p>
*  To get a source (sound coming from your speakers) use the getSoundComponent function and see its documention associated
*  	with it.
*/
public class SoundMaster {
	
	/** boolean value to signify if there was a critical error in setting up the Open Al audio engine */
	public boolean audioEngineOperational = false;
	
	/** boolean to signify when a listner component exists, so that only one may be set to active at a time */
	protected boolean listenerComponentExists = false;
	
	/** Maximum Data Buffers and emissions */
	public static final int NUM_BUFFERS = 32;
	public static final int NUM_SOURCES = 256;
	
	/**OpenAl engine configurations */
	private static final float maxSourceDistance = 500;
	private static final float refDistance = 25;
	private static final float dopVel = 1024 ;
	
	private static final float rolloffFactor = 0.5f;
	
	/** indexes of soundfile names and their associated position within the soundbuffer*/
	public Hashtable soundIndexes ;
	
	/** Buffers hold sound data. */
	protected IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);;
	
	/** Sources are points emitting sound. */
	protected IntBuffer sources = BufferUtils.createIntBuffer(NUM_SOURCES);;
	/** Boolean array of which parts of the source buffer actually have an active source in that spot in the buffer*/
	protected boolean[] sourceIsFilled; 
	
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
	 * Gets associated string with an OpenAl error code
	 * 
	   * 1) Identify the error code.
	   * 2) Return the error as a string.
	   */
	 private static String getALErrorString(int err) {
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
	   * Adds a source to the sources buffer and configures its properties, such as position and velocity
	   * 
	   * @param soundID the id of the sound, ie the location of the sound within the buffer
	   * @param toLoop boolean on if one wants this to loop
	   * @return the soundCode on pass
	   * 		 -1 on failure
	   */
	  protected int addSource(int soundID, boolean toLoop)
	  {
		  int position = -1;
		  int x = 0;
		  int errCode=0;
		  //find the position of the first unused spot in the source buffer  
		  for(int i = 0 ; i < NUM_SOURCES;i++)
		  {
			  if (sourceIsFilled[i]==false)
			  {
				  position = i;
				  break;
			  }
		  }
		  
		  //No space if buffer
		  if (position == -1)
		  {
			  return -1;
		  }
		  //source.limit(position + 1);
		  
		  AL10.alSourcei(sources.get(position), AL10.AL_BUFFER,   buffer.get(soundID) );
		  AL10.alSourcef(sources.get(position), AL10.AL_PITCH,    1.0f          );
		  AL10.alSourcef(sources.get(position), AL10.AL_GAIN,     1.0f          );
		  AL10.alSource (sources.get(position), AL10.AL_POSITION, sourcePos);
		  AL10.alSource (sources.get(position), AL10.AL_VELOCITY, sourceVel);
		  AL10.alSourcei(sources.get(position), AL10.AL_LOOPING,  (toLoop ? AL10.AL_TRUE : AL10.AL_FALSE));
		  
		  

		  AL10.alSourcef(sources.get(position), AL10.AL_REFERENCE_DISTANCE, refDistance);
		  AL10.alSourcef(sources.get(position), AL10.AL_ROLLOFF_FACTOR, 0.5f);
		  AL10.alSourcef(sources.get(position), AL10.AL_MAX_DISTANCE,  maxSourceDistance);

		  AL10.alSourcef(sources.get(position), AL10.AL_MIN_GAIN, 0.0f);
		  
		  if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
		  {
			  System.out.println(getALErrorString(errCode));
			  return -1;
			  
		  }
		  sourceIsFilled[position] = true; 
		  //source.position(position+1);
		  
		  return position;
		  
	  }
	  
	  /**
	   * Sets a souce to loop or not
	   * 
	   * @param soundID
	   * @param toLoop
	   * @return
	   */
	  protected boolean setLooping(int soundID, boolean toLoop)
	  {
		  
		  AL10.alSourcei(sources.get(soundID), AL10.AL_LOOPING,  (toLoop ? AL10.AL_TRUE : AL10.AL_FALSE));
		  
		  if (AL10.alGetError() != AL10.AL_NO_ERROR)
			  return false;
		  
		  return true;
	  
	  }
	  
		 protected void setRefDistance(int soundID, float refDistance)
		 {
			 
			 int errCode=0;
			 AL10.alSourcef(sources.get(soundID), AL10.AL_REFERENCE_DISTANCE, refDistance);
			  
			 if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
			  {
				  System.out.println(getALErrorString(errCode));
				  
				  
			  }
		 }
		 
		 protected void setSourceGain(int soundID, float gain){
			 
			 int errCode=0;
			 if(gain > 0)
			 {
				 AL10.alSourcef(sources.get(soundID), AL10.AL_GAIN, gain);
				 AL10.alSourcef(sources.get(soundID), AL10.AL_MAX_GAIN, gain);
			 }
			 
			 if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
			  {
				  System.out.println(getALErrorString(errCode));
				  
				  
			  } 
		 }
	  
	  /**
	   * Plays a source
	   * 
	   * @param soundCode - the code for the source or its position within the sources buffer
	   * @return
	   */
	  protected boolean playSource(int soundCode){
		  
		  int state = AL10.alGetSourcei(sources.get(soundCode), AL10.AL_SOURCE_STATE);
		  String errorString;
		  int errCode;
		  
		  
		  if(!sourceIsFilled[soundCode]){
			  return false;
		  }
		  if(state != AL10.AL_PLAYING)
		  {
			  AL10.alSourcePlay(sources.get(soundCode));
			  
		  }
		  
		  if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
		  {
			  
			  errorString= getALErrorString(errCode);
			  System.out.println(errorString);
			  return false;
		  }
		  
		  return true;

	  }
	  
	  protected boolean pauseSource(int soundCode)
	  {
		  int state = AL10.alGetSourcei(sources.get(soundCode), AL10.AL_SOURCE_STATE);
		  String errorString;
		  int errCode;
			
		  
		  if(state == AL10.AL_PLAYING)
		  {
			  AL10.alSourcePause(sources.get(soundCode));
			 
		  }
		  
		  if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
		  {
			  
			  errorString= getALErrorString(errCode);
			  System.out.println(errorString);
			  return false;
		  }
		  
		  return true;
		  
		  
		  
	  }
	  
	  /**
	   * Stops the playing of a source
	   * 
	   * @param soundCode - the code for the source or its position within the sources buffer
	   * @return
	   */
	  protected boolean stopSource(int soundCode)
	  {
		  int state = AL10.alGetSourcei(sources.get(soundCode), AL10.AL_SOURCE_STATE);
		  String errorString;
		  int errCode;
			
		  
		  if(state == AL10.AL_PLAYING)
		  {
			  AL10.alSourceStop(sources.get(soundCode));
			 
		  }
		  
		  if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
		  {
			  
			  errorString= getALErrorString(errCode);
			  System.out.println(errorString);
			  return false;
		  }
		  
		  return true;
		  
	  }
	  
	  /**
	   * Removes a source from the audio engine and reclaims its available buffer space
	   * 
	   * @param soundCode
	   * @return
	   */
	  protected boolean removeSource(int soundCode)
	  {
		  int errCode;
		  stopSource(soundCode);
		  
		  /*AL10.alDeleteSources(sources.get(soundCode));
		  
		  if ((errCode=AL10.alGetError()) != AL10.AL_NO_ERROR)
		  {
			  
			  
			  System.out.println(getALErrorString(errCode));
			  return false;
		  }

		  
		  sourceIsFilled[soundCode] = false;
		  */
		  return true;
		  
	  }
	  
	  
	  /**
	   * void killALData()
	   *
	   *  We have allocated memory for our buffers and sources which needs
	   *  to be returned to the system. This function frees that memory.
	   */
	  public void cleanUpALData() {
		  
		
		int position = sources.position();
	    sources.position(0).limit(position);
		
	    AL10.alDeleteSources(sources);
	    
	    AL10.alDeleteBuffers(buffer);
	    AL.destroy();
	  }
	  
	  
	  /**
	   * Sets the position of the source by using a vector given by x, y and z
	   * 
	   * @param x
	   * @param y
	   * @param z
	   * @param soundCode - the code for the source or its position within the sources buffer
	   */
	  protected void setSourcePosition(float x, float y , float z, int soundCode){
		  
		  AL10.alSource3f(sources.get( soundCode), AL10.AL_POSITION, x, y, z);
		  

		    
	  }
	  
	  /**
	   * Sets the velocity of a source by using a vector given by x, y and z
	   * 
	   * @param x
	   * @param y
	   * @param z
	   * @param soundCode - the code for the source or its position within the sources buffer
	   */
	  protected void setSourceVelocity(float x, float y, float z, int soundCode){
		  FloatBuffer vector = BufferUtils.createFloatBuffer(3).put(new float[] { x , y , z });
		  vector.flip();
		  
		  AL10.alSource (sources.get(soundCode), AL10.AL_VELOCITY, vector);
		    
		  
	  }
	  
	  /**
	   *   Sets the position of the listener by using a vector given by x, y and z
	   * 
	   * @param x
	   * @param y
	   * @param z
	   */
	  public void setListenerPosition(float x, float y , float z){
		  
		  listenerPos.put(0, x);
		  listenerPos.put(1, y);
		  listenerPos.put(2, z);
		  AL10.alListener3f(AL10.AL_POSITION, x, y, z);
		    
	  }
	  
	  /**
	   * Sets the velocity of the Listener by using a vector given by x, y and z
	   * 
	   * @param x 
	   * @param y
	   * @param z
	   */
	  public void setListenerVelocity(float x, float y , float z){
		  
		  listenerVel.put(0, x);
		  listenerVel.put(1, y);
		  listenerVel.put(2, z);
		  AL10.alListener3f(AL10.AL_VELOCITY, x, y, z);
		    
		  
	  }
	  
	 /**
	  * Sets the position of the Listener. 
	  * 
	  * @param x - nose vector x
	  * @param y - nose vector y
	  * @param z - nose vector z
	  */
	  public void setListenerOrientation(float x, float y , float z){
		  
		  listenerOri.put(0, x);
		  listenerOri.put(1, y);
		  listenerOri.put(2, z);
		  AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
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
	   *  This function will load our all audio data contained within soundIndexes into a buffer so that sources can be created for the game
	   *  	from this buffer
	   *  
	   *  Returns 1 on Success
	   *  Returns 0 on Fail
	   */
	  protected int loadAllSoundData() {
		    
		  InputStream is = null;
		  InputStream bufferedIs = null;
		  WaveData waveFile= null;
		  String fileName = "";
		  
		  AL10.alGenBuffers(buffer);
		  
		  Set soundFileNames =  soundIndexes.keySet();
		  
		  for(Iterator<String> it = soundFileNames.iterator(); it.hasNext(); ){
			  fileName = it.next();
			  
			  try {
					
					is = new FileInputStream(fileName);
					bufferedIs = new BufferedInputStream(is);
					waveFile = null;
					waveFile = WaveData.create(AudioSystem.getAudioInputStream(bufferedIs));
					
					AL10.alBufferData(buffer.get((Integer) soundIndexes.get(fileName)), waveFile.format, waveFile.data, waveFile.samplerate);	  
					waveFile.dispose();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Failed to load file: "+fileName );
					e.printStackTrace();
					
				}
			  
		  }
		  
		  // Do another error check and return.
		  if (AL10.alGetError() == AL10.AL_NO_ERROR)
		      return AL10.AL_TRUE;

		  return AL10.AL_FALSE;
		  
	  
	  }
	  
	  /**
	   * This function returns a sound emitter that is associated with one source.
	   * This emmiter creates a audio source.
	   * 
	   *  This emiter then can be used to start, play and in essence interact with the source
	   *  ie :
	   *  	SoundEmitter pianoComponent = this.soundMaster.getSoundComponent("assets/sound/piano2.wav",true);
			pianoComponent.stopSound();
			pianoComponent.playSound();
	   * 
	   * @param fileName the file name of the sound
	   * @param toLoop If the source sound is to be looped
	   * @return the soundEmitter
	   */
	  public SoundEmitter getSoundComponent(String fileName, boolean toLoop){
		  SoundEmitter sA = new SoundEmitter(this, fileName, toLoop);
		  
		  
		  return sA;
		  
	  }
	  
	  /**
	   * This function returns a ListenerComponent which provides game components, ie A kart
	   * This component will be created in one of two states, active and inactive
	   * 
	   * In the active state, the component will work as expected
	   * 
	   * In the inactive state, the component will function as a dummy component
	   * 
	   * 
	   * @param isActive Weather the component will be active or not
	   * @return
	   */
	  public ListenerComponent getListenerComponent(){
		  ListenerComponent Lc = new ListenerComponent(this, !listenerComponentExists);
		  listenerComponentExists = true;
		  return Lc;
	  }
	  
	  
	  /**
	   * Constructor for the sound master Class
	   * 
	   * Creates the audio engine itself as well as sets up buffers and data structures for the
	   * sound Master
	   * 
	   *  This function is were sound files are added to the sound buffer, so that a source may use it later
	   *  
	   *  To add a new sound file, add a new entry to the hash table soundIndex with the key being the 
	   *  	filename complete with path and then a new interger counting up from the previous soundIndexes entry.
	   *  	see the examples below
	   *  
	   *  As it is at this point the sound master can hold NUM_BUFFERS worth of sound files and NUM_SOURCES worth of sources
	   * 
	   */
	  public SoundMaster(){
		  
			sourceIsFilled = new boolean[NUM_SOURCES];
			Arrays.fill(sourceIsFilled, false);
			int i = 0;
			soundIndexes = new Hashtable();
			
			
			soundIndexes.put("assets/sound/ACiv Battle 2.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Car Accelerating.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Pew_Pew.wav", new Integer(i++));
			soundIndexes.put("assets/sound/piano2.wav", new Integer(i++));
			soundIndexes.put("assets/sound/FancyPants.wav", new Integer(i++));
			soundIndexes.put("assets/sound/alarma.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Missle_Launch_Mono.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Bleep 1.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Bleep 2.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Bunkai - Bunkai.wav", new Integer(i++));
			soundIndexes.put("assets/sound/FancyPants.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Angry Grunt - Sam.wav", new Integer(i++));
			soundIndexes.put("assets/sound/I gotcha now - Sam.wav", new Integer(i++));
			soundIndexes.put("assets/sound/I'll get you - Da Conti.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Look out for - Michael.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Oh Noo - Michael.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Race Musici.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Soske - Bunkai.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Victory!.wav", new Integer(i++));
			soundIndexes.put("assets/sound/Take that - Da Conti.wav", new Integer(i++));
			soundIndexes.put("assets/sound/carIdle.wav", new Integer(i++));
			soundIndexes.put("assets/sound/carMaxSpeed.wav", new Integer(i++));
			soundIndexes.put("assets/sound/car-brake.wav", new Integer(i++));
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
			  
			  AL10.alGenSources(sources);

			  if ((errCode = AL10.alGetError()) != AL10.AL_NO_ERROR)
			  {
				  audioEngineOperational = false;
				  errorString= getALErrorString(errCode);
				  System.out.println(errorString);
				  return;
			  }
			  
			  //Set doppler settings
			  AL10.alDopplerFactor(1.0f);
			  AL10.alDopplerVelocity(dopVel);
	  }
	  
	

}