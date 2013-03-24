package Graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.GL_HALF_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_MAX_COLOR_ATTACHMENTS;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector4f;

public class RenderTarget {
	

	private int fboId, colorId, normalId, positionId, depthId;
	
	private Vector4f clearColor;
	
	public RenderTarget(boolean multiChannel)
	{
		this.clearColor = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
		
		//init and bind
		this.fboId = glGenFramebuffers();
		this.colorId = glGenTextures();
		
		if(multiChannel)
		{
			this.normalId = glGenTextures();
			this.positionId = glGenTextures();
		}
		
		this.depthId = glGenRenderbuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);

		//set up texture
		addAttachment(colorId, 0, true);
		if(multiChannel)
		{
			addAttachment(normalId, 1, false);
			addAttachment(positionId, 2, false);
		}
		
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
		   
		
		IntBuffer drawBuf = BufferUtils.createIntBuffer(3);
        drawBuf.put(GL_COLOR_ATTACHMENT0);
        if(multiChannel)
        {
	        drawBuf.put(GL_COLOR_ATTACHMENT1);
	        drawBuf.put(GL_COLOR_ATTACHMENT2);
        }
        
        //FLIP IT OR YOU DIE
        drawBuf.flip();
        glDrawBuffers(drawBuf);
        
        //error checking
        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (status != GL_FRAMEBUFFER_COMPLETE)
        {
        	System.out.println("Error " + status + " in frame buffer object generation");
        }
        int lkj = glGetError();
        if( lkj != GL_NO_ERROR )
		{
			throw new RuntimeException("OpenGL error: "+org.lwjgl.util.glu.GLU.gluErrorString(lkj));
		} 
		

        //set alpha blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
		//unbind fbo
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}
	
	private void addAttachment(int id, int attachmentNo, boolean integerRgba)
	{
		glBindTexture(GL_TEXTURE_2D, id);
		
		//set params
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		
		//initialize size
		if(integerRgba)
		{
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 800, 600, 0,GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
		}
		else
		{									
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, 800, 600, 0, GL_RGBA, GL_FLOAT, (java.nio.ByteBuffer) null);
		}
		//attach to fbo
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + attachmentNo, GL_TEXTURE_2D, id, 0);

		//clear
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		
		//unbind here?
	}
	
	protected void set()
	{
		glBindTexture(GL_TEXTURE_2D, 0);//unbind texture
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);//bind fbo
		
		glViewport(0,0,800,600);//set to our texture size. adjust this later depending on the resolution and view size
		
		//clear it
		glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
		
		
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
	protected int getPosId()
	{
		return positionId;
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
