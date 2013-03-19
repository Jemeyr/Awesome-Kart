package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.util.vector.Matrix4f;

public class LightAccumulationBufferShader extends Shader{
	
	private int vert_id;
	private int frag_id;
	
	private int wMatIndex;
	private int vpMatIndex;
	
	private int camDirIndex;
	private Matrix4f viewProjection;

	
	protected int position_attr;
	protected int radUni;
	
	protected int outNorm, outCol, outDepth;
	
	public LightAccumulationBufferShader()
	{
		active = false;
				
    	vert_id = loadShader("light.glslv", GL_VERTEX_SHADER);
    	frag_id = loadShader("light.glslf", GL_FRAGMENT_SHADER);
    	
        shaderProgram = glCreateProgram();

        glAttachShader(shaderProgram, vert_id);
        glAttachShader(shaderProgram, frag_id);

        //set render target frag locations
        glBindFragDataLocation( shaderProgram, 0, "outColor");//TODO make the shader do this
        
        glLinkProgram(shaderProgram);

        wMatIndex = glGetUniformLocation(shaderProgram, "worldMatrix");
        vpMatIndex = glGetUniformLocation(shaderProgram, "vpMatrix");
		
        radUni = glGetUniformLocation( shaderProgram, "radius");
		
        
        camDirIndex = glGetAttribLocation(shaderProgram, "camDir");
        
		position_attr = glGetAttribLocation( shaderProgram, "position");
		
		
		
        viewProjection = new Matrix4f();
        
	}
		
	private void setTransform(Matrix4f world, Matrix4f vp)
	{
		glUniformMatrix4(wMatIndex, true, genFloatBuffer(world));
		glUniformMatrix4(vpMatIndex, true, genFloatBuffer(vp));
	}
	
	private void setScale(float f)
	{
		glUniform1f(radUni, f);
	}
	
	protected void useCam(Camera cam)
	{
		Matrix4f camProj = new Matrix4f(cam.projection);
		Matrix4f camView = new Matrix4f(cam.viewMat);
		Matrix4f camVP = new Matrix4f();
		
		camVP = Matrix4f.mul(camView, camProj, null);
		
		this.viewProjection = camVP;
		
		glUniform3f(camDirIndex, cam.direction.x, cam.direction.y, cam.direction.z);
	}

	protected void draw(Light l)
	{
		if(!active)
		{
			System.out.println("Shader has not begun");
			return;
		}

		setScale(l.rad);
		setTransform(l.getModelMat(), viewProjection);	//good good, also make sure to set the radius and color
		glBindVertexArray(Light.mesh.vao);
		
        glDrawElements(GL_TRIANGLES, Light.mesh.elementCount, GL_UNSIGNED_INT, 0);

		glBindVertexArray(0);
	}
	
	public int getPositionAttr()
	{
		return position_attr;
	}
	
	
}
