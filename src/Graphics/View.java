package Graphics;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;


import org.lwjgl.util.Rectangle;

public class View {
	protected Rectangle rect;
	protected Camera cam;
	
	private int fboId, texId, dbufId;
	
	protected void unsetRenderTarget()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
	}
	
	protected void setRenderTarget()
	{
		glViewport(0,0,800,600);//set to our texture size. adjust this later depending on the resolution and view size
		
		glBindTexture(GL_TEXTURE_2D, 0);//unbind texture
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);//bind fbo
		
		//clear fbo
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	}
	
	protected int getTexId()
	{
		return texId;
	}
	
	protected View(Rectangle r, Camera c)
	{
		this.rect = r;
		this.cam = c;
		
		
		//init and bind
		this.fboId = glGenFramebuffers();
		this.texId = glGenTextures();
		this.dbufId = glGenRenderbuffers();
		
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);

		//set up texture
		glBindTexture(GL_TEXTURE_2D, texId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 800, 600, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null); 
		
		
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texId);
		
		
		
		//set up depth buffer
		glBindRenderbuffer(GL_RENDERBUFFER, dbufId);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, 800, 600);
		

		glFramebufferRenderbuffer(GL_DEPTH_BUFFER, GL_DEPTH_ATTACHMENT, dbufId, 0);
		//??might be required
		
		//unbind fbo
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	
	
}
