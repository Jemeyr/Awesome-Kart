package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class LightAccumulationBufferShader extends Shader{
	
	private int vert_id;
	private int frag_id;
	
	
	
	private int wMatIndex;
	private int vpMatIndex;
	private int inverseVpMatIndex;
	
	private int camDirIndex;
	private Matrix4f viewProjection;
	private Matrix4f inverseViewProjection;

	
	protected int position_attr;
	protected int radUni, centerUni;
	
	protected int outCol;
	
	private int colTex, normTex, posTex;
	private int screenRect;
	
	public LightAccumulationBufferShader()
	{
		active = false;
				
    	vert_id = loadShader("light.glslv", GL_VERTEX_SHADER);
    	frag_id = loadShader("light.glslf", GL_FRAGMENT_SHADER);
    	
        shaderProgram = glCreateProgram();

        glAttachShader(shaderProgram, vert_id);
        glAttachShader(shaderProgram, frag_id);

        //set render target frag locations
        glBindFragDataLocation( shaderProgram, 0, "outColor");

        glLinkProgram(shaderProgram);
        
        glUseProgram(shaderProgram);

        //Get the input texture positions
		colTex = glGetUniformLocation(shaderProgram, "colTex");
		normTex = glGetUniformLocation(shaderProgram, "normTex");
		posTex = glGetUniformLocation(shaderProgram, "posTex");
		

		screenRect = glGetUniformLocation(shaderProgram, "screenRect");
		
		glUniform1i(colTex, 0);//bind fbo color indices to them
		glUniform1i(normTex, 1);
		glUniform1i(posTex, 2);
        
        wMatIndex = glGetUniformLocation(shaderProgram, "worldMatrix");
        vpMatIndex = glGetUniformLocation(shaderProgram, "vpMatrix");
        inverseVpMatIndex = glGetUniformLocation(shaderProgram, "ivpMatrix");
		
        radUni = glGetUniformLocation( shaderProgram, "radius");
        centerUni = glGetUniformLocation( shaderProgram, "center");
		
        
        camDirIndex = glGetAttribLocation(shaderProgram, "camDir");
        
		position_attr = glGetAttribLocation( shaderProgram, "position");
		

		setRectSize();//TODO: make this more extensible 600 800
		
		viewProjection = new Matrix4f();
		inverseViewProjection = new Matrix4f();
        
        
	}
		
	private void setTransform(Matrix4f world, Matrix4f vp, Matrix4f ivp)
	{
		glUniformMatrix4(wMatIndex, true, genFloatBuffer(world));
		glUniformMatrix4(vpMatIndex, true, genFloatBuffer(vp));
		
		glUniformMatrix4(inverseVpMatIndex, true, genFloatBuffer(ivp));
	}
	
	private void setUniforms(Light l)
	{
		glUniform3f(centerUni, l.getPosition().x, l.getPosition().y, l.getPosition().z );
		glUniform1f(radUni, l.rad);
	}
	
	protected void useCam(Camera cam)
	{
		Matrix4f camProj = new Matrix4f(cam.projection);
		Matrix4f camView = new Matrix4f(cam.viewMat);
		Matrix4f camVP = new Matrix4f();
		
		camVP = Matrix4f.mul(camView, camProj, null);
		
		this.viewProjection = camVP;
		
		Matrix4f.invert(viewProjection, inverseViewProjection);
		
		glUniform3f(camDirIndex, cam.direction.x, cam.direction.y, cam.direction.z);
	}
	
	protected void setRectSize()
	{
		//1/800, 1/600
		glUniform2f(screenRect, 0.00125f, 0.001666f);
	}

	protected void draw(Light l, View v)
	{
		if(!active)
		{
			System.out.println("Shader has not begun");
			return;
		}

		setUniforms(l);
		setTransform(l.getModelMat(), viewProjection, inverseViewProjection);	//good good, also make sure to set the radius and color
		glBindVertexArray(Light.mesh.vao);

		//bind all the texture info from the Gbuffer
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, v.getColorTexture());

		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, v.getNormalTexture());
		
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, v.getPosTexture());
		
        glDrawElements(GL_TRIANGLES, Light.mesh.elementCount, GL_UNSIGNED_INT, 0);

		glBindVertexArray(0);
	}
	
	public int getPositionAttr()
	{
		return position_attr;
	}
	
	
}
