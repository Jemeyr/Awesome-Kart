package Graphics;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {

	private int vert_id;
	private int frag_id;
	
	private int wvpMatIndex;
	private int camDirIndex;
	
	
	private int shaderProgram;
	
	private boolean active;
	
	private Matrix4f viewProjection;
	
	public Shader()
	{
		active = false;
		
    	vert_id = loadShader("default_vertex.glslv", GL_VERTEX_SHADER);
    	frag_id = loadShader("default_frag.glslf", GL_FRAGMENT_SHADER);
    	

        shaderProgram = glCreateProgram();
        
        glAttachShader(shaderProgram, vert_id);
        glAttachShader(shaderProgram, frag_id);
        
        glBindFragDataLocation( shaderProgram, 0, "outColor");
        
        glLinkProgram(shaderProgram);
        
        wvpMatIndex = glGetUniformLocation(shaderProgram, "wvpMatrix");
        camDirIndex = glGetUniformLocation(shaderProgram, "camDir");
        
        viewProjection = new Matrix4f();
        
	}
	
	private void setTransform(Matrix4f mat)
	{
		
        glUniformMatrix4(wvpMatIndex, true, genFloatBuffer(mat));
        
	}
	
	protected void useCam(Camera cam)
	{
		Matrix4f transform = new Matrix4f();
		transform.setIdentity();
		
		Matrix4f.mul(cam.projection, cam.viewMat, this.viewProjection);
	
		glUniform3f(camDirIndex, cam.direction.x, cam.direction.y, cam.direction.z);
	}

	protected void begin()
	{
		glUseProgram(shaderProgram);
		active = true;
	}
	
	protected void end()
	{
		glUseProgram(0);
		active = false;
	}
	
	protected void draw(DebugGraphicsComponent gc)
	{
		if(!active)
		{
			System.out.println("Shader has not begun");
			return;
		}
		
		Matrix4f transform = new Matrix4f();
		transform.setIdentity();
		

		
		Matrix4f.mul(viewProjection, gc.getModelMat(), transform);
		
		setTransform(transform);

		

        glUseProgram(0);
	}
	
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
	
	
	
	public FloatBuffer genFloatBuffer(Matrix4f input)
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
}
