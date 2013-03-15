package Graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
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
	protected int normal_attr;
	protected int texCoord_attr;
	
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
		
        camDirIndex = glGetUniformLocation(shaderProgram, "camDir");

        
		position_attr = glGetAttribLocation( shaderProgram, "position");
        normal_attr = glGetAttribLocation( shaderProgram, "normal");
        texCoord_attr = glGetAttribLocation( shaderProgram, "texCoord");

        //MRT
        outCol = glGetAttribLocation( shaderProgram, "outColor");
        
		
        viewProjection = new Matrix4f();
        
	}
		
	private void setTransform(Matrix4f world, Matrix4f vp)
	{
		glUniformMatrix4(wMatIndex, true, genFloatBuffer(world));
		glUniformMatrix4(vpMatIndex, true, genFloatBuffer(vp));
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

	
	protected void draw(DebugGraphicsComponent gc)
	{
		if(!active)
		{
			System.out.println("Shader has not begun");
			return;
		}
	
		bindDraw(gc);
		for(DebugGraphicsComponent sgc : gc.subComponents)
		{
			draw(sgc);
		}
		
		
	}

	private void bindDraw(DebugGraphicsComponent gc)
	{
		setTransform(gc.getModelMat(), viewProjection);
		
		glBindVertexArray(gc.mesh.vao);
		
		glActiveTexture(GL_TEXTURE0);//to texture 0
        glBindTexture(GL_TEXTURE_2D, gc.mesh.texId);

        glDrawElements(GL_TRIANGLES, gc.mesh.elementCount, GL_UNSIGNED_INT, 0);

        glBindTexture(GL_TEXTURE_2D, 0);

		glBindVertexArray(0);

	}
	
	


	
}
