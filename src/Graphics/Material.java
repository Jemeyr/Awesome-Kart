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
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class Material {

	int vert_id;
	int frag_id;
	int shaderProgram;
	
	public Material()
	{

    	vert_id = loadShader("default_vertex.glslv", GL_VERTEX_SHADER);
    	frag_id = loadShader("default_frag.glslf", GL_FRAGMENT_SHADER);
    	

        shaderProgram = glCreateProgram();
        
        glAttachShader(shaderProgram, vert_id);
        glAttachShader(shaderProgram, frag_id);
        
        glBindFragDataLocation( shaderProgram, 0, "outColor");
        
        glLinkProgram(shaderProgram);
        
	}
	
	protected void setTransform(Matrix4f mat)
	{
		//set shader thing here.
		
	}

	protected void draw(Camera cam, DebugGraphicsComponent gc)
	{
		glUseProgram(shaderProgram);
		
		Matrix4f transform = new Matrix4f();
		transform.setIdentity();
		
		Matrix4f.mul(cam.projection, cam.viewMat, transform);
		Matrix4f.mul(transform, gc.getModelMat(), transform);
		
		setTransform(transform);
		
		
		//

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
	
}
