package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;


public class RenderTarget {
	

	private int fboId, colorId, normalId, depthId;// positionId;
	
	public RenderTarget()
	{

		//init and bind
		this.fboId = glGenFramebuffers();
		this.colorId = glGenTextures();
		this.normalId = glGenTextures();
		//this.positionId = glGenTextures();
		
		this.depthId = glGenRenderbuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);

		//set up texture
		addAttachment(colorId, 0);
		addAttachment(normalId, 1);
		//addAttachment(positionId, 2);
		
		
		//set up depth buffer
		glBindRenderbuffer(GL_RENDERBUFFER, depthId);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, 800, 600);
		
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, depthId);
		
		
		IntBuffer temp = BufferUtils.createIntBuffer(16);
				
		glGetInteger(GL_MAX_COLOR_ATTACHMENTS, temp);
		if(temp.get(0) < 4)
		{
			System.out.println("ERROR: Support for color attachments is not sufficient. Fuggg.");
		}
		   
		
		IntBuffer drawBuf = BufferUtils.createIntBuffer(2);
        drawBuf.put(GL_COLOR_ATTACHMENT0);
        drawBuf.put(GL_COLOR_ATTACHMENT1);
        //drawBuf.put(GL_COLOR_ATTACHMENT2);
        
        //this breaks things for now
        //glDrawBuffers(drawBuf);
        
        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (status != GL_FRAMEBUFFER_COMPLETE)
        {
        	System.out.println("Error " + status + " in frame buffer object generation");
        }
/*        int err = glGetError();
        if( err != GL_NO_ERROR )
		{
			System.out.println("ERROR IN RT " + err + " is not " + GL_NO_ERROR);

			System.out.println("org.lwjgl.util.glu.GLU.gluErrorString(glGetError())");
		}*/
     
        
		//unbind fbo
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}
	
	private void addAttachment(int id, int attachmentNo)
	{
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 800, 600, 0,GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachmentNo, GL_TEXTURE_2D, id, 0);
		
		
	}
	
	protected void set()
	{
		glBindTexture(GL_TEXTURE_2D, 0);//unbind texture
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);//bind fbo
		
		glViewport(0,0,800,600);//set to our texture size. adjust this later depending on the resolution and view size
		
		//clear fbo

		glClearColor(0.4f, 0.8f, 0.9f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	}
	
	protected void unset()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);	
	}

	protected int getColId()
	{
		return colorId;
	}
	protected int getNormId()
	{
		return normalId;
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
}
