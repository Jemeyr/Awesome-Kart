package Sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;


public class SoundMaster {
	
	/** Buffers hold sound data. */
	protected IntBuffer buffer = BufferUtils.createIntBuffer(1);
  
	/** Sources are points emitting sound. */
	protected IntBuffer source = BufferUtils.createIntBuffer(1);
	
	 /** Position of the source sound. */
	protected FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
	  /** Velocity of the source sound. */
	protected FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
	  /** Position of the listener. */
	protected FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
	  /** Velocity of the listener. */
	protected FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
	
	  /** Orientation of the listener. (first 3 elements are "at", second 3 are "up") */
	protected FloatBuffer listenerOri =
	      BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f });
	  
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
	  protected int loadSoundData() {
		  sourcePos.position(0);
		  sourceVel.position(0);
		  listenerPos.position(0);
		  listenerVel.position(0);
		  listenerOri.position(0);
		  
		  AL10.alGenBuffers(buffer);
		  
		  
		  //Check if there was an error creating the buffer, if there was return fail
		  if (AL10.alGetError() != AL10.AL_NO_ERROR)
			  return AL10.AL_FALSE;
		  InputStream is = null;
		  InputStream bufferedIs = null;
		  WaveData waveFile= null;
		  
			try {
				is = new FileInputStream("assets/sound/swvader02.wav");
				
				bufferedIs = new BufferedInputStream(is);
				
				
				waveFile = WaveData.create(AudioSystem.getAudioInputStream(bufferedIs));
			
				//is = new FileInputStream("./assets/sound/FancyPants.wav");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			
		  //WaveData waveFile = WaveData.create("/assets/sound/Cantina1.mid");
		  

		  
		  AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
		  
		  waveFile.dispose();
		  
		  AL10.alGenSources(source);

		  if (AL10.alGetError() != AL10.AL_NO_ERROR)
			  return AL10.AL_FALSE;

		  AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
		  AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f          );
		  AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f          );
		  AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos     );
		  AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel     );
		  
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
	   * void killALData()
	   *
	   *  We have allocated memory for our buffers and sources which needs
	   *  to be returned to the system. This function frees that memory.
	   */
	  void cleanUpALData() {
	    AL10.alDeleteSources(source);
	    AL10.alDeleteBuffers(buffer);
	    AL.destroy();
	  }
	  
	  
	  /**
	   * public void execute()
	   * 
	   */
	  
	  public void execute(){
		// Initialize OpenAL and clear the error bit.
		  
		  
		  try{
			AL.create();  
		  }
		  catch(LWJGLException le){
			le.printStackTrace();
			return;
		  }
		  
		  AL10.alGetError();
		  
		  if(loadSoundData()==AL10.AL_FALSE){
			System.out.println("Error Loading data.");
			return;
		  }
		  
		  setListenerValues();
		  
	  }
	  
	  public void play(){
		  int state = AL10.AL_PLAYING;
		  
		  AL10.alSourcePlay(source.get(0));
		  
		  while(state == AL10.AL_PLAYING){
			  System.out.println("Playing audio");
			  try {
				Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("sleep failed");
					e.printStackTrace();
				}
			  state = AL10.alGetSourcei(source.get(0), AL10.AL_SOURCE_STATE);
		  }
		  
		  System.out.println("done playing audio");
		  
		  cleanUpALData();
	  }
	

}
