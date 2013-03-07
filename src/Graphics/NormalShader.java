package Graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
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

public class NormalShader extends Shader{

	
	private int vert_id;
	private int frag_id;
	
	private int wvpMatIndex;
	private int camDirIndex;
	private Matrix4f viewProjection;
	
	protected int position_attr;
	protected int normal_attr;
	protected int texCoord_attr;
	
	
	
	public NormalShader()
	{
		active = false;
				
    	vert_id = loadShader("vertexShader.glslv", GL_VERTEX_SHADER);
    	frag_id = loadShader("fragmentShader.glslf", GL_FRAGMENT_SHADER);

        shaderProgram = glCreateProgram();
        
        glAttachShader(shaderProgram, vert_id);
        glAttachShader(shaderProgram, frag_id);
        
        glBindFragDataLocation( shaderProgram, 0, "outColor");
        
        glLinkProgram(shaderProgram);
       
		wvpMatIndex = glGetUniformLocation(shaderProgram, "wvpMatrix");
        camDirIndex = glGetUniformLocation(shaderProgram, "camDir");
        
		position_attr = glGetAttribLocation( shaderProgram, "position");
        normal_attr = glGetAttribLocation( shaderProgram, "normal");
        texCoord_attr = glGetAttribLocation( shaderProgram, "texCoord");

        
        viewProjection = new Matrix4f();
        
	}
		
	private void setTransform(Matrix4f mat)
	{
		glUniformMatrix4(wvpMatIndex, true, genFloatBuffer(mat));
	}
	
	protected void useCam(Camera cam)
	{
		//System.out.println("Shader: setting camera");
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
		
		//Matrix4f.mul(viewProjection, gc.getModelMat(), transform);
	
		bindDraw(gc);
		for(DebugGraphicsComponent sgc : gc.subComponents)
		{
			draw(sgc);
		}
		
		
	}

	private void bindDraw(DebugGraphicsComponent gc)
	{
		Matrix4f transform = new Matrix4f();
		Matrix4f.mul(gc.getModelMat(), viewProjection, transform);
		
		setTransform(transform);
		
		glBindVertexArray(gc.mesh.vao);

        glBindTexture(GL_TEXTURE_2D, gc.mesh.texId);

        glDrawElements(GL_TRIANGLES, gc.mesh.elementCount, GL_UNSIGNED_INT, 0);

        glBindTexture(GL_TEXTURE_2D, 0);

		glBindVertexArray(0);

	}
	
	


	
}
