package Graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL20.*;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Rectangle;

public class ViewShader extends Shader{

	protected int vao;
	protected int vbo_v,vbo_t,elem;
	protected int elementCount;
	
	
	private int vert_id;
	private int frag_id;
	
	protected int position_attr;
	protected int texCoord_attr;

	private int posUniform, sizeUniform;

	private int tex0, tex1;
	
	public ViewShader()
	{
		active = false;
				
		
    	vert_id = loadShader("viewV.glslv", GL_VERTEX_SHADER);
    	frag_id = loadShader("viewF.glslf", GL_FRAGMENT_SHADER);
    	
        this.shaderProgram = glCreateProgram();
        
        glAttachShader(shaderProgram, vert_id);
        glAttachShader(shaderProgram, frag_id);
        
        glLinkProgram(shaderProgram);
        
        glUseProgram(shaderProgram);
        
		position_attr = glGetAttribLocation( shaderProgram, "position");

        glBindFragDataLocation( shaderProgram, 0, "outColor");


        //MRT
		tex0 = glGetUniformLocation(shaderProgram, "tex0");//returns 2
		tex1 = glGetUniformLocation(shaderProgram, "tex1");//returns 3

//no effect?
//		glUseProgram(shaderProgram);
		glUniform1i(tex0, 0);
		glUniform1i(tex1, 1);
//		glUseProgram(0);
		
		
		posUniform = glGetUniformLocation(shaderProgram, "pos");
		sizeUniform = glGetUniformLocation(shaderProgram, "size");

		
		//make a rectangle
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        

        vbo_v = glGenBuffers();
        elem = glGenBuffers();
        
        float[] f = {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
        int[] e = {0,1,2,0,2,3};
        
        
        elementCount = e.length;
        IntBuffer ebuff = BufferUtils.createIntBuffer(e.length);
        ebuff.put(e);
        ebuff.rewind();
        
		FloatBuffer vbuff = genFloatBuffer(f);
		
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glBufferData(GL_ARRAY_BUFFER, vbuff , GL_STATIC_DRAW);
        glVertexAttribPointer( position_attr, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elem);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ebuff, GL_STATIC_DRAW);
        
        glEnableVertexAttribArray(position_attr);

        
        glBindVertexArray(0);
 
	}
	
	
	protected void draw(View v)
	{
		if(!active)
		{
			System.out.println("Shader has not begun");
			return;
		}
		//set uniforms from the view
		glUniform2f(posUniform,  v.screenPos[0], v.screenPos[1]);
		glUniform2f(sizeUniform, v.screenSize[0], v.screenSize[1]);

		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, v.getColorTexture());
		
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, v.getNormalTexture());

		//bind vao for the rect
		glBindVertexArray(vao);
				
		glDrawElements(GL_TRIANGLES, elementCount, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

	}

}
