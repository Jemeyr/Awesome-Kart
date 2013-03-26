package Graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL14.glBlendEquation;
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

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class LightAccumulationBufferShader extends Shader{
	
	private int vert_id;
	private int frag_id;
	
	private int wMatIndex;
	private int vpMatIndex;
	
	private int camDirIndex;
	private Matrix4f viewProjection;
	private Matrix4f invView;
	private Vector3f camPosition;

	
	protected int position_attr;
	protected int radUni, centerUni, colorUni;
	
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
		
        radUni = glGetUniformLocation( shaderProgram, "radius");
        centerUni = glGetUniformLocation( shaderProgram, "center");
        colorUni = glGetUniformLocation( shaderProgram, "lightColor");
        
        camDirIndex = glGetAttribLocation(shaderProgram, "camDir");
        
		position_attr = glGetAttribLocation( shaderProgram, "position");
		

		setRectSize();//TODO: make this more extensible 600 800
		
		viewProjection = new Matrix4f();
		invView = new Matrix4f();
        camPosition = new Vector3f();
	}
		
	private void setTransform(Light l, Matrix4f world, Matrix4f vp)
	{
		Matrix4f modelMat = new Matrix4f();

		
		
		//compensate for internal light surfaces
		Vector3f camDist = new Vector3f();
		Vector3f.sub(camPosition, l.getPosition(), camDist);
		if(camDist.lengthSquared() < 2*l.rad*l.rad)
		{

			modelMat.m00 = 10f;
			modelMat.m11 = 10f;
			modelMat.m22 = 10f;

			modelMat.m03 = 0f;
			modelMat.m13 = 0f;
			modelMat.m23 = 1f;
			Matrix4f.mul(this.invView, modelMat, modelMat);
		}
		else
		{
			Matrix4f.mul(this.invView, world, modelMat);
		}
		//glUniformMatrix4(wMatIndex, true, genFloatBuffer(world));
		glUniformMatrix4(wMatIndex, true, genFloatBuffer(modelMat));
		
		glUniformMatrix4(vpMatIndex, true, genFloatBuffer(vp));
	}
	
	private void setUniforms(Light l)
	{
		glUniform3f(centerUni, l.getPosition().x, l.getPosition().y, l.getPosition().z );
		glUniform1f(radUni, l.rad);
		glUniform3f(colorUni, l.getColor().x, l.getColor().y, l.getColor().z);
	}
	
	protected void useCam(Camera cam)
	{
		Matrix4f camProj = new Matrix4f(cam.projection);
		Matrix4f camView = new Matrix4f(cam.viewMat);
		Matrix4f camVP = new Matrix4f();
		
		camVP = Matrix4f.mul(camView, camProj, null);
		
		//store inverse view matrix
		Matrix4f.invert(cam.viewMat, this.invView);

		this.invView.m30 = 0.0f;
		this.invView.m31 = 0.0f;
		this.invView.m32 = 0.0f;
		
		this.invView.m03 = 0.0f;
		this.invView.m13 = 0.0f;
		this.invView.m23 = 0.0f;
		this.invView.m33 = 1.0f;
		
		this.viewProjection = camVP;
		
		this.camPosition = cam.position;
		
		glUniform3f(camDirIndex, cam.direction.x, cam.direction.y, cam.direction.z);
	}
	
	protected void setRectSize()
	{
		//1/800, 1/600
		glUniform2f(screenRect, 0.00125f, 0.001666f);
	}

	protected void begin()
	{
		super.begin();
	    glEnable(GL_BLEND);
	    
	    glDisable(GL_ALPHA_TEST);
	    glDisable(GL_DEPTH_TEST);
	    
	    glBlendFunc(GL_SRC_ALPHA, GL_DST_ALPHA);
	    //glBlendFunc(GL_ONE,GL_ONE);
	    glBlendEquation(GL_FUNC_ADD);

	    
	}
	
	
	protected void end()
	{
		glEnable(GL_DEPTH_TEST);
	    
		glEnable(GL_ALPHA_TEST);
	    glDisable(GL_BLEND);

//	    glStencilFunc(GL_NOTEQUAL, 0, 0xFF);
    	
		super.end();
	}
	
	protected void draw(Light l, View v)
	{
		if(!active)
		{
			System.out.println("Shader has not begun");
			return;
		}

		setUniforms(l);
		setTransform(l, l.getModelMat(), viewProjection);	//good good, also make sure to set the radius and color
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
