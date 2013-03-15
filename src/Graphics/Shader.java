package Graphics;

import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public abstract class Shader {
	protected int shaderProgram;
	
	static protected boolean active;
	
	public int loadShader(String fileName, int shaderType)
	{
		
		int shader = -1;
		
		//load the file into a string
		String fileString = "";
		String temp = "";

		try{
			BufferedReader in = new BufferedReader(new FileReader("assets/shaders/" + fileName)); 
		
			while((temp = in.readLine()) != null)
			{
				fileString += temp + "\n";
			}
			in.close();
			
		}catch	(Exception e)
		{
			System.out.println("Shader: " + e);
			return -1;
		}
		//load the shader from its source
		String shaderSource = new String(fileString);
		
		shader = glCreateShader(shaderType);
		
        glShaderSource(shader, shaderSource );
        glCompileShader(shader);

        //error check
        IntBuffer infoLogLength = BufferUtils.createIntBuffer(1);
        glGetShader(shader, GL_INFO_LOG_LENGTH, infoLogLength);
        ByteBuffer infoLog = BufferUtils.createByteBuffer(infoLogLength.get(0));
        infoLogLength.clear();
        glGetShaderInfoLog(shader, infoLogLength, infoLog);
        byte[] infoLogBytes = new byte[infoLogLength.get(0)];
        infoLog.get(infoLogBytes, 0, infoLogLength.get(0));
        String res = new String(infoLogBytes);
        
        if(res.contains("error"))
        {
        	System.out.println("Error loading " + fileName + "\n");
        	System.out.println("Hey, this means you should upgrade your opengl driver and perhaps your graphics card\n");
        	System.out.println(res);
        	return -1;
        }
        
		return shader;
	}
	
	
	protected void begin()
	{
		if(active)
		{
			System.out.println("shader cannot be begun while another is active");
			return;
		}
		glUseProgram(shaderProgram);
		active = true;
	}
	
	protected void end()
	{
		glUseProgram(0);
		active = false;
	}
	
	protected FloatBuffer genFloatBuffer(Matrix4f input)
	{
		
        FloatBuffer fbuff = null;
        try{
        	fbuff = BufferUtils.createFloatBuffer(16);
	        input.store(fbuff);
        	
	        fbuff.rewind();
        }
        catch (Exception e)
        {
        	System.out.println(e);
        	return null;
        }
        
        return fbuff;
		
	}


	protected FloatBuffer genFloatBuffer(float[] input)
	{
		
		FloatBuffer fbuff = null;
		try{
			fbuff = BufferUtils.createFloatBuffer(input.length);
			fbuff.put(input);
		
			fbuff.rewind();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
		return fbuff;
	}
	
	public int getShaderProgram()
	{
		return this.shaderProgram;
	}
	
	public int getPositionAttr()
	{
		return -1;
	}
	
	public int getNormalAttr()
	{
		return -1;
	}
	
	public int getTexCoordAttr()
	{
		return -1;
	}
	
}
